// import './assets/main.css'

import { createApp } from 'vue';
import App from './App.vue'; // компонент vue
import router from "./router.js";

createApp(App).use(router).mount('#app');
