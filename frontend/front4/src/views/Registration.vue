<template>
    <div id="content">
        <h1>Вход в систему</h1>
        <hr>
        <form id="form" @submit.prevent="logIn">
            <div id="login">
                <label for="loginInput">Введите логин: </label>
                <input type="text"
                       id="loginInput"
                       required
                       placeholder="Логин"
                       v-model.trim="login"/>
            </div>
            <div id="password">
                <label for="passwordInput">Введите пароль: </label>
                <input type="password"
                       id="passwordInput"
                       required
                       placeholder="Пароль"
                       v-model.trim="password"/>
            </div>
            <div id="buttons">
                <button @click.native="logIn">Войти</button>
                <button @click.native="register">Зарегистрироваться</button>
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
            password: "",
        }
    },
    methods: {
        // отправка данных на сервер для авторизации
        logIn(e) {
            if (this.login !== "" && this.password !== "") {
                e.preventDefault(); // предотвращает перезагрузку страницы
                const base64Credentials = btoa(`${this.login}:${this.password}`);
                // отправка данных по юрл
                this.$axios.post('http://localhost:8080/api/login', null, {
                    headers: {
                        'Authorization': `Basic ${base64Credentials}`
                    }
                }).then(response => {
                    localStorage.setItem("jwt", response.data);
                    localStorage.setItem("login", this.login);
                    console.log(localStorage.getItem("jwt"));
                    this.$router.push({name: 'app-page'});
                }).catch(error => {
                    this.AxiosErrorHandler(error.response.data);
                });
            } else {
                this.AxiosErrorHandler("Введите данные")
            }

        },
        // отправка данных на сервер для регистрации
        register(e) {
            if (this.login !== "" && this.password !== "") {
                e.preventDefault();
                this.$axios.post('http://localhost:8080/api/register', {
                    login: this.login,
                    password: this.password
                }).then(response => {
                    // если рега прошла успешно, пользователь перенаправляется
                    // на основную страницу и сохраняется токен авторизации
                    localStorage.setItem("jwt", response.data);
                    localStorage.setItem("login", this.login);
                    this.$router.push({name: 'app-page'});
                }).catch(error => {
                    this.AxiosErrorHandler(error.response.data);
                });
            } else {
                this.AxiosErrorHandler("Введите данные");
            }
        },
        // вывод сообщения об ошибке
        AxiosErrorHandler(errorText) {
            this.$notify({
                group: 'error',
                title: 'Ошибка',
                text: errorText,
                type: 'error'
            })
        }
    }
}
</script>

<style scoped>
#content {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 80vh; /* Высота 100% экрана */
}
div {
    padding-bottom: 5px;
}

#buttons button {
    margin-right: 10px; /* Расстояние между кнопками */
}

</style>