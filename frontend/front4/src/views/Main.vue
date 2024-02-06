<template>
    <div id="app">
<!--        <Header v-bind="headerParams"/>-->
        <MyCanvas/>
        <InputForm/>
    </div>
</template>

<script>
import Header from "@/components/Header.vue";
import MyCanvas from "@/components/MyCanvas.vue";
import InputForm from "@/components/InputForm.vue";

export default {
    name: 'App',
    components: {
        Header,
        MyCanvas,
        InputForm
    },
    data() {
        return {
            headerParams: {
                title: "Лабораторная работа №4",
                authorName: "Яхонтов Максим"
            }
        }
    }
}

</script>

<style scoped>
body {
    margin: 0;
    display: flex;
    min-height: 100vh;
}
</style>
