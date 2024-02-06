// import './assets/main.css'

import { createApp } from 'vue';
import App from './App.vue'; // компонент vue
import router from "./router.js";
import axios from "axios";
import Notifications from "@kyvg/vue3-notification";

const app = createApp(App);
app.use(router);
app.use(Notifications);
app.config.globalProperties.$axios = axios;
app.mount('#app');