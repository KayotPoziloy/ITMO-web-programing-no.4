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
            beforeEnter: (to, from, next) => {
                (localStorage.getItem("jwt") !== null) ? next({name: 'app-page'}) : next({name: 'auth-page'});
            }
        },
        {
            path: '/auth',
            name: 'auth-page',
            component: Registration,
            beforeEnter: (to, from, next) => {
                if (localStorage.getItem("jwt") == null) {
                    next();
                } else {
                    next({name: 'app-page'});
                }
            }
        },
        {
            path: '/app',
            name: 'app-page',
            component: Main,
            beforeEnter: (to, from, next) => {
                if (localStorage.getItem("jwt") !== null) next();
                else next({
                    name: 'auth-page',
                });
            }
        }
    ]
});

export default router;
