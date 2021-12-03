---
title: uniCloud初体验
date: 2021-10-01 11:54:32
cover: https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/7.jpg
tags:
  - uniCloud
categories: javascript
---

# uniCloud是什么
uniCloud是DCloud在阿里云和腾讯云的serverless服务上封装而成的。
它包含IaaS层（由阿里云和腾讯云提供硬件和网络）和PaaS层（由DCloud提供开发环境）。
开发者可以自主选择uniCloud的硬件和网络资源的供应商，在阿里云版和腾讯云版之间切换。
开户和付费虽然通过DCloud渠道，但实际上开发者自动在云厂商处建立了账户和充值了余额。DCloud只获取云服务厂商的返佣。
开发时虽使用DCloud的工具，但应用上线时，手机端是直连阿里云或腾讯云的serverless，不经由DCloud的服务器。

# uniCloud通讯录案例
首先使用HbuilderX新建项目：
![在这里插入图片描述](https://img-blog.csdnimg.cn/f95475b2929c4dd99069e20b9469c3fd.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)注意勾选启用uniCloud，个人推荐使用阿里云，因为使用阿里云的话可以免费使用云存储：
![在这里插入图片描述](https://img-blog.csdnimg.cn/9753bcaa8f934e3e84dfe2deeda900b8.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)创建好后会出现以下目录，其中uniCloud是跟数据库和云函数有关的文件夹，项目打包的前端页面里不会将其打包进去。
![在这里插入图片描述](https://img-blog.csdnimg.cn/e8154b42e86848c3bf4ea22469e2cb8b.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_15,color_FFFFFF,t_70,g_se,x_16)
然后就可以创建一下云服务空间，就可以使用云服务了，直接选中uniCloud文件夹右击，点击打开uniCloud web控制台，就可以创建云空间了：
![在这里插入图片描述](https://img-blog.csdnimg.cn/64ae74ca8dc14263ae3a3bcff2f83802.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)创建好云服务空间后就可以直接创建一个数据表，然后再加两条数据：
![在这里插入图片描述](https://img-blog.csdnimg.cn/58304184617c46e9a069b1e25ed2620f.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)
然后回到编译器，创建以下目录：
![在这里插入图片描述](https://img-blog.csdnimg.cn/72293fe3043d43518905d50551d141f0.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_14,color_FFFFFF,t_70,g_se,x_16)
引入uni-ui，实现代码如下：

list.vue代码：
```vue
<template>
	<view>
		<!-- 列表展示与删除 -->
		<unicloud-db ref="udb" v-slot:default="{data, loading, error, options}" collection="test">
			<view v-if="error">{{error.message}}</view>
			<view v-else>
				<uni-list>
					<uni-list-item 
					    v-for="item in data"
						@longpress.native="rmItem(item._id)"
						@click.native="update(item)"
						:key="item._id" 
						:title="item.name"
						:note="item.phone"
						link
					>
					</uni-list-item>
				</uni-list>
			</view>
		</unicloud-db>
		
		<!-- 新增数据 -->
		<uni-easyinput v-model="item.name" placeholder="name"></uni-easyinput>
		<uni-easyinput v-model="item.phone" placeholder="phone"></uni-easyinput>
		<button type="default" @click="submit">submit</button>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				item: {
					"name": "",
					"phone": ""
				}
			}
		},
		methods: {
			// 删除功能
			rmItem(id) {
				this.$refs.udb.remove(id);
			},
			
			// 新增功能
			submit() {
				const db = uniCloud.database();
				db.collection('test').add(this.item).then(e=>{
					console.log(e);
				})
			},
			
			// 修改数据
			update(item) {
				uni.navigateTo({
					url: '../update/update?item=' + JSON.stringify(item),
					success: res => {},
					fail: () => {},
					complete: () => {}
				});
			},
		}
	}
</script>

<style>

</style>
```

update代码：

```vue
<template>
	<view>
		<!-- 修改数据 -->
		<uni-easyinput v-model="item.name" placeholder="name"></uni-easyinput>
		<uni-easyinput v-model="item.phone" placeholder="phone"></uni-easyinput>
		<button type="default" @click="update()">submit</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				item: {
					"_id": "",
					"name": "",
					"phone": ""
				}
			}
		},
		onLoad({item}) {
			this.item = JSON.parse(item);
		},
		methods: {
			// 修改数据
			update() {
				const db = uniCloud.database();
				let item = {...this.item}
				delete item._id
				db.collection('test').doc(this.item._id).update(item).then(e=>{
					console.log(e);
				})
			},
		}
	}
</script>

<style>
</style>
```
test.schema.json如下：

```json
{
	"bsonType": "object",
	"required": [],
	"permission": {
		"read": true,
		"create": true,
		"update": true,
		"delete": true
	},
	"properties": {
		"_id": {
			"description": "ID，系统自动生成"
		},
		"name": {
			
		},
		"phone": {
			
		}
	}
}
```
效果如下，点击列表即可修改，长按即可删除，输入即可新增，相较于传统前后端开发模式，使用uniCloud确实可以大大简化流程。
![在这里插入图片描述](https://img-blog.csdnimg.cn/ba18b2e3014d4a04892a83a1e22e85b1.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAZXllcysr,size_20,color_FFFFFF,t_70,g_se,x_16)

