import { createWebHistory, createRouter } from 'vue-router';
import Registration from "@/views/Registration.vue";
import Main from "@/views/Main.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            name: 'default-page',
            component: Registration,
        },
        {
            path: '/auth',
            name: 'auth-page',
            component: Registration,
        },
        {
            path: '/app',
            name: 'app-page',
            component: Main,
        }
    ]
});

export default router;
