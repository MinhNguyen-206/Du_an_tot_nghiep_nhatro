package com.nhatro.backend.repository.specification;

import com.nhatro.backend.entity.NhaTro;
import com.nhatro.backend.entity.TienIch;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

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
            predicates.add(root.get("loaiPhong").in((Object[]) types));
        }

        return predicates;
    }
}