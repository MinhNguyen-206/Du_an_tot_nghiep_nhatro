export function formatDate(value, options = {}) {
  if (!value) return ''
  const date = new Date(value)
  if (isNaN(date.getTime())) return ''
  return new Intl.DateTimeFormat('vi-VN', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    ...options
  }).format(date)
}

export function formatDateTime(value) {
  return formatDate(value, { hour: '2-digit', minute: '2-digit' })
}
