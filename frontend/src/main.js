import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import './index.css' // giả định có css chung, nếu không thì bỏ

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
