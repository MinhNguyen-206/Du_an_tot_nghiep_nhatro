package com.nhatro.backend.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.nhatro.backend.entity.NhaTro;
import com.nhatro.backend.entity.TienIch;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class NhaTroSpecification {

    public static Specification<NhaTro> filterNhaTro(
            String keyword, String location, Double minPrice, Double maxPrice, 
            String[] types, Boolean wifi, Boolean ac, Boolean parking, Boolean camera, Boolean pet) {

        return (root, query, cb) -> {
            // Loai bỏ bản ghi trùng lặp khi thực hiện Join bảng tiện ích
            if (query != null) {
                query.distinct(true);
            }

            List<Predicate> predicates = getBasePredicates(keyword, location, minPrice, maxPrice, types, root, cb);

            // 5. Lọc các tiện ích bằng cách JOIN vào tập hợp danhSachTienIch
            if (Boolean.TRUE.equals(wifi)) {
                Join<NhaTro, TienIch> joinWifi = root.join("danhSachTienIch");
                predicates.add(cb.like(cb.lower(joinWifi.get("tenTienIch")), "%wifi%"));
            }

            if (Boolean.TRUE.equals(ac)) {
                Join<NhaTro, TienIch> joinAc = root.join("danhSachTienIch");
                predicates.add(cb.or(
                    cb.like(cb.lower(joinAc.get("tenTienIch")), "%điều hòa%"),
                    cb.like(cb.lower(joinAc.get("tenTienIch")), "%máy lạnh%"),
                    cb.like(cb.lower(joinAc.get("tenTienIch")), "%ac%")
                ));
            }

            if (Boolean.TRUE.equals(parking)) {
                Join<NhaTro, TienIch> joinParking = root.join("danhSachTienIch");
                predicates.add(cb.or(
                    cb.like(cb.lower(joinParking.get("tenTienIch")), "%giữ xe%"),
                    cb.like(cb.lower(joinParking.get("tenTienIch")), "%đỗ xe%"),
                    cb.like(cb.lower(joinParking.get("tenTienIch")), "%bãi xe%")
                ));
            }

            if (Boolean.TRUE.equals(camera)) {
                Join<NhaTro, TienIch> joinCamera = root.join("danhSachTienIch");
                predicates.add(cb.like(cb.lower(joinCamera.get("tenTienIch")), "%camera%"));
            }

            if (Boolean.TRUE.equals(pet)) {
                Join<NhaTro, TienIch> joinPet = root.join("danhSachTienIch");
                predicates.add(cb.or(
                    cb.like(cb.lower(joinPet.get("tenTienIch")), "%thú cưng%"),
                    cb.like(cb.lower(joinPet.get("tenTienIch")), "%nuôi thú%")
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<NhaTro> filterNhaTro(
            String keyword, String location, Double minPrice, Double maxPrice, 
            String[] types, String[] amenities) {

        return (root, query, cb) -> {
            if (query != null) {
                query.distinct(true);
            }
            List<Predicate> predicates = getBasePredicates(keyword, location, minPrice, maxPrice, types, root, cb);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Map moi checkbox "loai phong" tren giao dien sang tap tu khoa (chu
     * thuong, khong dau tieng Viet duoc bo qua vi DB da co dau) de so
     * khop LIKE voi cot loaiPhong trong DB. Ly do can map thay vi so
     * khop tuyet doi: du lieu mau dat ten loai phong tu do (vd "Phong
     * tieu chuan", "Phong co ban cong"...) khong trung 100% voi 5 gia
     * tri co dinh tren UI ("Chung cu mini", "Ki tuc xa", "Phong tro",
     * "Studio", "Nha nguyen can").
     */
    private static List<String> mapLoaiPhongKeywords(String type) {
        String t = type.toLowerCase();
        List<String> keywords = new ArrayList<>();
        if (t.contains("chung cư") || t.contains("chung cu")) {
            keywords.add("chung cư");
            keywords.add("căn hộ mini");
            keywords.add("can ho mini");
        } else if (t.contains("ký túc") || t.contains("ky tuc") || t.contains("túc xá")) {
            keywords.add("ký túc");
            keywords.add("sinh viên");
        } else if (t.contains("studio")) {
            keywords.add("studio");
        } else if (t.contains("nguyên căn") || t.contains("nguyen can")) {
            keywords.add("nguyên căn");
            keywords.add("nhà nguyên");
        } else if (t.contains("phòng trọ") || t.contains("phong tro")) {
            keywords.add("phòng");
            keywords.add("căn hộ");
        } else {
            // Fallback: so khop LIKE truc tiep voi chinh gia tri checkbox
            keywords.add(t);
        }
        return keywords;
    }

    private static List<Predicate> getBasePredicates(
            String keyword, String location, Double minPrice, Double maxPrice, 
            String[] types, Root<NhaTro> root, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            String kw = "%" + keyword.trim().toLowerCase() + "%";
            Predicate nameLike = cb.like(cb.lower(root.get("tenNhaTro")), kw);
            Predicate descLike = cb.like(cb.lower(root.get("moTa")), kw);
            predicates.add(cb.or(nameLike, descLike));
        }

        if (location != null && !location.trim().isEmpty()) {
            String loc = location.trim().toLowerCase();
            if (loc.contains("hồ chí minh") || loc.contains("hcm") || loc.contains("tp.hcm")) {
                Predicate p1 = cb.like(cb.lower(root.get("diaChi")), "%tp.hcm%");
                Predicate p2 = cb.like(cb.lower(root.get("diaChi")), "%hồ chí minh%");
                Predicate p3 = cb.like(cb.lower(root.get("diaChi")), "%hcm%");
                predicates.add(cb.or(p1, p2, p3));
            } else {
                predicates.add(cb.like(cb.lower(root.get("diaChi")), "%" + loc + "%"));
            }
        }

        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("giaPhong"), minPrice));
        }
        if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("giaPhong"), maxPrice));
        }

        if (types != null && types.length > 0) {
            List<Predicate> typePredicates = new ArrayList<>();
            for (String type : types) {
                if (type == null || type.trim().isEmpty()) {
                    continue;
                }
                for (String keyword2 : mapLoaiPhongKeywords(type.trim())) {
                    typePredicates.add(cb.like(cb.lower(root.get("loaiPhong")), "%" + keyword2 + "%"));
                }
            }
            if (!typePredicates.isEmpty()) {
                predicates.add(cb.or(typePredicates.toArray(new Predicate[0])));
            }
        }

        return predicates;
    }
}