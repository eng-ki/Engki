<template>
  <div class="my-modal" v-if="visible" @click.self="handleWrapperClick">
    <div class="my-modal__dialog">
      <header class="my-modal__header">
        <span>
          <img src="../static/img/icon/chest.png" style="width:10%;" />
          {{title}}
        </span>
        <button @click="$emit('update:visible', !visible)" style="float:right;">X</button>
      </header>
      <div class="my-modal__body">
        <slot>
          <table>
            <tbody id="contacts">
              <tr>
                <td>
                  <img src="../static/img/icon/jungle/001-toucan.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/002-monkey.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/003-waterfall.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/004-palm tree.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/005-castaway.png" style="width:50%;" />
                </td>
              </tr>
              <tr>
                <td>
                  <img src="../static/img/icon/jungle/006-jungle.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/007-hippopotamus.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/008-plant.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/009-chameleon.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/010-backpack.png" style="width:50%;" />
                </td>
              </tr>
              <tr>
                <td>
                  <img src="../static/img/icon/jungle/006-jungle.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/007-hippopotamus.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/008-plant.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/009-chameleon.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/010-backpack.png" style="width:50%;" />
                </td>
              </tr>
              <tr>
                <td>
                  <img src="../static/img/icon/jungle/006-jungle.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/007-hippopotamus.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/008-plant.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/009-chameleon.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/010-backpack.png" style="width:50%;" />
                </td>
              </tr>
              <tr>
                <td>
                  <img src="../static/img/icon/jungle/006-jungle.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/007-hippopotamus.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/008-plant.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/009-chameleon.png" style="width:50%;" />
                </td>
                <td>
                  <img src="../static/img/icon/jungle/010-backpack.png" style="width:50%;" />
                </td>
              </tr>
            </tbody>
          </table>

          <!-- real data -->
          <!-- <div v-for="(item, index) in itemlist" v-bind:key="index">
            <img :src="`item`" />
          </div>-->
        </slot>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "Inventory",
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
      this.$emit("update:visible", false);
    },
  },
};
</script>
<style lang="scss">
$module: "my-modal";
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
  }
  &__body {
    background: white;
    padding: 25px;
    min-height: 150px;
    max-height: 412px;
    overflow-y: scroll;
  }
}

tr {
  background-color: lightgray;
}
</style>