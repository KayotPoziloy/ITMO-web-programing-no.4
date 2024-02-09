<template>
    <div id="content">
        <h1>Вход в систему</h1>
        <hr>
        <form id="form" @submit.prevent="logIn">
            <div id="login">
                <label for="loginInput">Введите логин:</label>
                <input type="text"
                       id="loginInput"
                       required
                       placeholder="Логин"
                       v-model.trim="login"/>
            </div>
            <div id="password">
                <label for="passwordInput">Введите пароль:</label>
                <input type="password"
                       id="passwordInput"
                       required
                       placeholder="Пароль"
                       v-model.trim="password"/>
            </div>
            <div id="buttons">
                <button @click.native="logIn">Войти</button>
                <button @click.native="register">Зарегистрироваться</button>
                <button @click.native="test">TEST</button>

            </div>
        </form>
    </div>
</template>

<script>
export default {
    name: "Registration",
    data() {
        return {
            login: "",
            password: ""
        }
    },
    methods: {
        logIn(e) {
            e.preventDefault(); // предотвращает перезагрузку страницы
            const base64Credentials = btoa(`${this.login}:${this.password}`);
            // отправка данных по юрл
            this.$axios.post('http://localhost:8080/api/login', null, {
                headers: {
                    'Authorization': `Basic ${base64Credentials}`
                }
            }).then(response => {
                this.$router.push({name: 'app-page'});
            }).catch(error => {
                this.AxiosErrorHandler(error.response.data);
            });
            console.log("отправлен " + this.login);
        },
        register(e) {
            e.preventDefault();
            this.$axios.post('http://localhost:8080/api/register', {
                login: this.login,
                password: this.password
            }).then(response => {
                // если рега прошла успешно, пользователь перенаправляется на основную страницу
                this.$router.push({name: 'app-page'});
            }).catch(error => {
                this.AxiosErrorHandler(error.response.data);
            });
            console.log("отправлен" + this.login);
        },
        test(e) {
            e.preventDefault();
            this.$axios.get('http://localhost:8080/user/all');
        },
        AxiosErrorHandler(errorText) {
            this.$notify({
                group: 'error',
                title: 'Error',
                text: errorText,
                type: 'error'
            })
        }
    }
}
</script>

<style scoped>

</style>