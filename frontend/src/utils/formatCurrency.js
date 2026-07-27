export function formatCurrency(value) {
  if (value === null || value === undefined || isNaN(value)) return ''
  return new Intl.NumberFormat('vi-VN', {
    style: 'currency',
    currency: 'VND'
  }).format(value)
}
