<template>
  <div class="my-modal" v-if="visible" @click.self="handleWrapperClick">
    <div class="my-modal__dialog">
      <header class="my-modal__header">
        <span>
          <img src="../static/img/icon/chest.png" style="width: 10%" />
          {{ title }}
        </span>
        <button
          @click="$emit('update:visible', !visible)"
          style="float: right; color: white"
        >
          X
        </button>
      </header>
      <div class="my-modal__body">
        <table>
          <tbody id="contacts">
            <tr v-for="j in 10" :key="j">
              <td v-for="i in 5" :key="i">
                <img :src="itemlist[0]" style="width: 50%" />
              </td>
            </tr>
          </tbody>
        </table>

        <!-- real data -->
        <!-- <div v-for="(item, index) in itemlist" v-bind:key="index">
            <img :src="`item`" />
        </div>-->
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'Inventory',
  props: {
    visible: {
      type: Boolean,
      require: true,
      default: false,
    },
    title: {
      type: String,
      require: false,
    },
    itemlist: {
      type: Array,
      require: false,
      default: false,
    },
  },
  methods: {
    handleWrapperClick() {
      this.$emit('update:visible', false)
    },
  },
}
</script>
<style lang="scss">
$module: 'my-modal';
.#{$module} {
  // This is modal bg
  background-color: rgba(0, 0, 0, 0.7);
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  position: fixed;
  overflow: auto;
  margin: 0;
  //This is modal layer
  &__dialog {
    left: 38%;
    top: 75px;
    width: 30%;
    position: absolute;
    background: #62a205;
    margin-bottom: 50px;
  }

  &__header {
    font-size: 28px;
    font-weight: bold;
    line-height: 1.29;
    padding: 16px 16px 0 25px;
    position: relative;
    color: #8ebf43;
  }
  &__body {
    background: white;
    padding: 0px;
    min-height: 150px;
    max-height: 412px;
    overflow-y: scroll;
    &::-webkit-scrollbar {
      width: 10px;
      background: none;
    }
    &::-webkit-scrollbar-thumb {
      background: white;
      opacity: 0.4;
    }
    &::-webkit-scrollbar-track {
      background: #62a205;
    }
  }

  table {
    border: bold outset #62a205;
    background-color: #62a205;
    padding: 4px;
  }
  td {
    border: thin inset #62a205;
    background-color: #8ebf43;
    margin: 4px;
  }
}
</style>
