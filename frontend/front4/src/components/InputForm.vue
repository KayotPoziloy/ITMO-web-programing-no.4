<template>
    <div class="Input">
        <form @submit.prevent="handleSubmit">
            <label for="xInput">Координата X:</label>
            <select v-model="x" id="xInput">
                <option v-for="value in xValues" :key="value">{{ value }}</option>
            </select>

            <label for="yInput">Координата Y(-5,...,3):</label>
            <input v-model="y" id="yInput" type="text" @input="validateY"/>

            <label for="rInput">Радиус R:</label>
            <select v-model="r" id="rInput">
                <option v-for="value in rValues" :key="value">{{ value }}</option>
            </select>

            <button type="submit">Отправить</button>
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
        handleSubmit() {
            console.log("отправлено")
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