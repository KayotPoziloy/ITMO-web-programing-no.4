<template>
    <div class="Input">
        <form @submit.prevent="handleSubmit">
            <div>
                <label for="xInput">Координата X:</label>
                <select v-model="x" id="xInput">
                    <option v-for="value in xValues" :key="value">{{ value }}</option>
                </select>
            </div>

            <div>
                <label for="yInput">Координата Y(-5,...,3):</label>
                <input v-model="y" id="yInput" type="text"/>
            </div>

            <div >
                <label for="rInput">Радиус R:</label>
                <select ref="rInput" v-model="r" id="rInput">
                    <option v-for="value in rValues" :key="value">{{ value }}</option>
                </select>
            </div>

            <button type="submit" v-on:click="sending">Отправить</button>
        </form>
    </div>
</template>

<script>
import { reactive } from 'vue';
export const state = reactive({ rValue: -5 });


export default {
    data() {
        return {
            x: '-5',
            y: '',
            r: '-5',
            xValues: ['-5', '-4', '-3', '-2', '-1', '0', '1', '2', '3'],
            rValues: ['-5', '-4', '-3', '-2', '-1', '0', '1', '2', '3'],
            xValidValues: ['-5', '-4', '-3', '-2', '-1', '0', '1', '2', '3'],
            rValidValues: ['-5', '-4', '-3', '-2', '-1', '0', '1', '2', '3'],
        };
    },
    // отлов изменений в r для определения reactive-переменной (глобальной)
    watch: {
        r: function (newR, oldR) {
            this.handleRChange(newR);
        }
    },
    methods: {
        // присвоение reactive-переменной (глобальной) нового значения
        handleRChange(newR) {
            state.rValue = newR;
        },
        // валидация данных
        validateY() {
            if (!isNaN(parseFloat(this.y)) && parseFloat(this.y) >= -5 && parseFloat(this.y) <= 3) {
                return true;
            } else {
                return false;
            }
        },
        validateX() {
            if (this.xValidValues.includes(this.x)) {
                return true;
            } else {
                return false;
            }
        },
        validateR() {
            if (this.rValidValues.includes(this.r)) {
                return true;
            } else {
                return false;
            }
        },

        // отправка точек на сервер
        sending(e) {
            if (this.validateX() && this.validateY() && this.validateR()) {
                e.preventDefault();
                const data = {
                    'x': this.x.replace(",", "."),
                    'y': this.y.replace(",", "."),
                    'r': this.r.replace(",", "."),
                    'owner': localStorage.getItem("login")
                }


                this.$axios.post('http://localhost:8080/api/dots', data);
                console.log("отправлено");
            } else {
                this.AxiosErrorHandler("Введены неверные данные");
                console.log("не отправлено");
            }
        },
        // вывод сообщения об ошибке
        AxiosErrorHandler(errorText) {
            this.$notify({
                group: 'error',
                title: 'Ошибка',
                text: errorText,
                type: 'error'
            });
        }
    }
}

</script>

<style scoped>
.Input {
    color: black;
    text-align: center;
    font-family: serif;
    background-color: #d7d7d4;
    height: 25%;
}
</style>