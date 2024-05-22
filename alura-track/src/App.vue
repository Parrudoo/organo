<template>
  <main class="columns is-gapless is-multiline">

    <div class="column is-one-quarter">
      <BarraLateral/>
    </div>

    <div class="column is-three-quarter">
      <Formulario/>
    </div>

  </main>
</template>

<script>
import { defineComponent } from 'vue'
import BarraLateral from './components/BarraLatarel.vue';
import Formulario from './components/Formulario'

export default defineComponent({
  name: 'App', 
  components: {   
    BarraLateral,
    Formulario

  }}
);

</script>

<style>

</style>
