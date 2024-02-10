<template>
    <div class="Input">
        <form @submit.prevent="handleSubmit">
            <div>
                <label for="xInput">Координата X:</label>
                <select v-model="x" id="xInput">
                    <option v-for="value in xValues" :key="value" @input="validateX">{{ value }}</option>
                </select>
            </div>

            <div>
                <label for="yInput">Координата Y(-5,...,3):</label>
                <input v-model="y" id="yInput" type="text" @input="validateY"/>
            </div>

            <div>
                <label for="rInput">Радиус R:</label>
                <select v-model="r" id="rInput">
                    <option v-for="value in rValues" :key="value" @input="validateR">{{ value }}</option>
                </select>
            </div>

            <button type="submit" v-on:click="sending">Отправить</button>
        </form>
    </div>
</template>

<script>
export default {
    data() {
        return {
            x: '-5',
            y: '',
            r: '-5',
            xValues: ['-5', '-4', '-3', '-2', '-1', '0', '1', '2', '3'],
            rValues: ['-5', '-4', '-3', '-2', '-1', '0', '1', '2', '3']
        };
    },
    methods: {
        validateY() {
            console.log("проверено")
        },
        validateX() {

        },
        validateR() {

        },
        handleSubmit() {
            console.log("отправлено")
        },
        // отправка точек на сервер
        sending(e) {
            e.preventDefault();
            this.$axios.post('http://localhost:8080/дописать', {
                x: this.x,
                y: this.y,
                r: this.r
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