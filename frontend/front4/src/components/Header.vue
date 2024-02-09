<template>
    <header class="header">
        <div>
            <span class="lab-name">{{title}}</span>
        </div>
        <div>
            <span>
              <a>{{authorName}}</a>, P3220
            </span>
            <span>
              Вариант
              <a>20001</a>
            </span>
        </div>
        <button v-on:click="exit">Выход</button>
    </header>
</template>

<script>
export default {
    name: "Header",
    props: {
        title: String,
        authorName: String,
    },
    methods: {
        exit() {
            localStorage.removeItem("jwt");
            window.location.reload();
        }
    }
}
</script>

<style scoped>
.header {
    /*color: #ff9aa4;*/
    color: black;
    text-align: center;
    font-family: serif;
    background-color: #d7d7d4;
    height: 25%;

}
</style>