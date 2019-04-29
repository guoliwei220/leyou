<template>
    <div>
      <v-layout class="px-3 pd-2">
        <v-flex xs2>
          <v-btn small color="info" @click="addBrand">新增品牌</v-btn>
        </v-flex>
        <v-spacer/>
        <v-flex xs4>
          <v-text-field label="搜索" hide-details="" append-icon="search" v-model="search"></v-text-field>
        </v-flex>
      </v-layout>
      <v-data-table
        :headers="headers"
        :items="desserts"
        :pagination.sync="pagination"
        :total-items="totalDesserts"
        :loading="loading"
        class="elevation-1"
      >
        <template slot="items" slot-scope="props" >
          <td class="text-xs-center">{{ props.item.id }}</td>
          <td class="text-xs-center">{{ props.item.name }}</td>
          <td class="text-xs-center"><img src="props.item.image" alt=""></td>
          <td class="text-xs-center">{{ props.item.letter }}</td>
          <td class="text-xs-center">
            <v-btn flat icon color="info">
              <v-icon>edit</v-icon>
            </v-btn>
            <v-btn flat icon color="error">
              <v-icon>delete</v-icon>
            </v-btn>
          </td>
        </template>
      </v-data-table>
      <!--弹出的对话框-->
      <v-dialog max-width="500" v-model="show" persistent scrollable>
        <v-card>
          <!--对话框的标题-->
          <v-toolbar dense dark color="primary">
            <v-toolbar-title>{{isEdit ? '修改' : '新增'}}品牌</v-toolbar-title>
            <v-spacer/>
            <!--关闭窗口的按钮-->
            <v-btn icon @click="closeWindow"><v-icon>close</v-icon></v-btn>
          </v-toolbar>
          <!--对话框的内容，表单-->
          <v-card-text class="px-5" style="height:400px">
            <brand-form @close="closeWindow" :oldBrand="oldBrand" :isEdit="isEdit"/>
          </v-card-text>
        </v-card>
      </v-dialog>
    </div>
</template>

<script>
  // 导入自定义的表单组件
  import BrandForm from './BrandForm'
  export default {
        name: "MyBrand.vue",
        data(){
          return{
            headers:[
              {
                text:"品牌id",
                value:"id",
                align: 'center',
                sortable:true,
              },
              {
                text:"品牌名称",
                value:"name",
                align: 'center',
                sortable:false,
              },
              {
                text:"品牌LOGO",
                value:"image",
                align: 'center',
                sortable:false,
              },
              {
                text:"品牌首字母",
                value:"letter",
                align: 'center',
                sortable:true,
              },
              {
                text:"操作",
                value:"",
                align: 'center',
                sortable:false,
              }
            ],
            desserts:[],
            pagination:{},
            totalDesserts:0,
            loading:false,
            search:"",
            show: false,// 控制对话框的显示
            oldBrand: {}, // 即将被编辑的品牌数据
            isEdit: false, // 是否是编辑
          }
        },
      created(){
          /*this.$http.get("/brand/page",{
            params:{
              page:1,
            }
          }).then(resp=>{})*/
          /*this.desserts=[
            {id:201,name:"小米",image:"1.img",letter:"X"},
            {id:200,name:"华为",image:"1.img",letter:"H"},
            {id:211,name:"联想",image:"1.img",letter:"L"},
            {id:301,name:"小度",image:"1.img",letter:"X"}
          ];
          this.totalDesserts = 15;*/
          this.loadBrands();
      },
      watch:{
        search(){
            this.pagination.page = 1;
            this.loadBrands();
          },
          pagination:{
            deep:true,
              handler(){
              this.loadBrands();
            }
        },
      },
      methods:{
          loadBrands(){
            this.$http.get("/item/brand/page",{
              params:{
                page:this.pagination.page,
                rows:this.pagination.rowsPerPage,
                sortBy:this.pagination.sortBy,
                desc:this.pagination.descending,
                key:this.search
              }
            }).then(resp=>{
              console.log(resp);
              this.desserts = resp.data.items,
              this.totalDesserts = resp.data.total,
              this.loading = true
            })
          },
        addBrand() {
          // 修改标记
          this.isEdit = false;
          // 控制弹窗可见：
          this.show = true;
          // 把oldBrand变为null
          this.oldBrand = null;
        },
        editBrand(oldBrand){
          // 根据品牌信息查询商品分类
          this.$http.get("/item/category/bid/" + oldBrand.id)
            .then(({data}) => {
              // 修改标记
              this.isEdit = true;
              // 控制弹窗可见：
              this.show = true;
              // 获取要编辑的brand
              this.oldBrand = oldBrand
              // 回显商品分类
              this.oldBrand.categories = data;
            })
        },
        closeWindow(){
          // 重新加载数据
          this.loadBrands();
          // 关闭窗口
          this.show = false;
        }
      },
      components:{
        BrandForm
      }
    }
</script>

<style scoped>

</style>
