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
            this.$router.push({name: 'app-page'}) // перенаправляет пользователя на страницу
            // отправка данных по юрл
            this.$axios.post('http://localhost:8080/test', {
                login: this.login,
                password: this.password
            }).then(response => { // обработка успешного ответа
                localStorage.setItem("jwt", response.data);
                this.$router.push({name: 'app-page'});
            }).catch(error => {
                this.AxiosErrorHandler(error.response.data);
            });
        },
        register(e) {
            e.preventDefault();
            this.$axios.put('http://localhost:8890/api/user', {
                login: this.login,
                password: this.password
            }).then(() => {
                this.$notify({
                    group: 'success',
                    title: 'Регистрация',
                    text: 'Теперь, вы можете войти',
                    type: 'success'
                });
            }).catch(error => {
                this.AxiosErrorHandler(error.response.data);
            });
        },
        test(e) {
            e.preventDefault();
            this.$axios.get('http://localhost:8080/test', {
                login: this.login,
                password: this.password
            })
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