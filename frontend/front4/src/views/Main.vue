<template>
    <div id="app">
<!--        <Header v-bind="headerParams"/>-->
        <table border="1" cellpadding="0" cellspacing="0" width="100%">
            <tbody>
                <tr>
                    <td colspan="2">
                        <MyCanvas/>
                    </td>
                </tr>
                <tr style="background-color: #d7d7d4;">
                    <td>
                        <InputForm/>
                    </td>
                    <td style="width: 70%;">
                        <table border="1" cellpadding="0" cellspacing="0" width="90%" class="result-table">
                            <tr>
                                <th>X</th>
                                <th>Y</th>
                                <th>R</th>
                                <th>Результат</th>
                            </tr>
                            <tr>
                                <td>1</td>
                                <td>1</td>
                                <td>1</td>
                                <td>да</td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </tbody>
        </table>


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
.result-table {
    margin: 0 auto;
}
</style>
