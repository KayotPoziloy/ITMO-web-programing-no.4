<template>
  <div id="app">
      <table border="1" cellpadding="0" cellspacing="0" width="100%">
          <thead>
          <Header v-bind="headerParams"/>
          </thead>
          <tbody>
          <router-view/>
          </tbody>
      </table>


  </div>
  <div>
      <notifications group="error" position="bottom right"/>
      <notifications group="success" position="bottom right"/>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import {Notifications} from "@kyvg/vue3-notification";


export default {
    name: 'App',
    components: {
        Notifications,
        Header,
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
