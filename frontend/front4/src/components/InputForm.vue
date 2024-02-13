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

<!--            <MyCanvas :rInputRef="rInput"/>-->

            <button type="submit" v-on:click="sending">Отправить</button>
        </form>
    </div>
</template>

<script>
import { reactive } from 'vue';
// import {state} from "@/main.js";
export const state = reactive({ rValue: 1 });


export default {
    // setup() {
    //     const state = reactive({ rValue: 1});
    //     return {state};
    // },
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
    watch: {
        r: function (newR, oldR) {
            console.log("новый радиус", newR);
            this.handleRChange(newR);
        }
    },
    methods: {
        handleRChange(newR) {
            state.rValue = newR;
        },
        validateY() {
            if (!isNaN(parseFloat(this.y)) && parseFloat(this.y) >= -5 && parseFloat(this.y) <= 3) {
                console.log("проверено y")
                return true;
            } else {
                console.log("не проверено y")
                // вывод ошибки
                return false;
            }
        },
        validateX() {
            if (this.xValidValues.includes(this.x)) {
                console.log("проверено x")
                return true;
            } else {
                console.log("не проверено x")
                // вывод ошибки
                return false;
            }
        },
        validateR() {
            if (this.rValidValues.includes(this.r)) {
                console.log("проверено r")
                return true;
            } else {
                console.log("не проверено r")
                // вывод ошибки
                return false;
            }
        },
        handleSubmit() {
        },
        // отправка точек на сервер
        sending(e) {
            if (this.validateX() && this.validateY() && this.validateR()) {
                e.preventDefault();
                const data = {
                    'x': this.x,
                    'y': this.y,
                    'r': this.r,
                    'owner': localStorage.getItem("login")
                }


                this.$axios.post('http://localhost:8080/api/dots', data);
                console.log("отправлено");
            } else {
                console.log("не отправлено");
            }
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