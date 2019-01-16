<template>
	<div class="pokemon">
		<div class="show">
			<div class="img">
				<img :src="info.imgPath" alt="">
			</div>
			<el-row :gutter="20">
			  <el-col :span="12">
			  	<div class="pokemon-name">
			  		<span>#{{('00' + id).slice(-3)}}</span>
			  		{{list[id - 1].name}}
			  	</div>
			  </el-col>
			  <el-col :span="12">
			  	<el-select v-model="id" filterable placeholder="请选择" @change="getInfo">
				    <el-option
				      v-for="item in list"
				      :key="item.id"
				      :label="'#' + ('00' + item.id).slice(-3) +'  ' + item.name"
				      :value="item.id">
				    </el-option>
				  </el-select>
			  </el-col>
			</el-row>		
		</div>
		<div class="compute">
			<div class="row-title">
				<el-row :gutter="20">
			 		 <el-col :span="4">能力</el-col>
			 		 <el-col :span="4">种族值</el-col>
			 		 <el-col :span="4">个体值</el-col>
			 		 <el-col :span="4">努力值</el-col>
			 		 <el-col :span="4">性格</el-col>
			 		 <el-col :span="4">能力值</el-col>
				</el-row>	
			</div>
			<div class="row-item">
				<el-row :gutter="20">
			 		 <el-col :span="4">HP</el-col>
			 		 <el-col :span="4">{{info.hp}}</el-col>
			 		 <el-col :span="4">
			 		 	<el-input placeholder="请输入内容" @change="checkGtInp(0)" v-model="sendMsg.geti[0]"></el-input>
					 </el-col>
			 		 <el-col :span="4">
			 		 	<el-input placeholder="请输入内容" @change="checkNlInp(0)" v-model="sendMsg.nuli[0]"></el-input>
			 		 </el-col>
			 		 <el-col :span="4">
			 		 	<el-button @click="resetxg" type="primary">无性格修正</el-button>
			 		 </el-col>
			 		 <el-col :span="4">{{getMsg[0]}}</el-col>
				</el-row>	
			</div>
			<div class="row-item">
				<el-row :gutter="20">
			 		 <el-col :span="4">物攻</el-col>
			 		 <el-col :span="4">{{info.atk}}</el-col>
			 		 <el-col :span="4">
			 		 	<el-input placeholder="请输入内容" @change="checkGtInp(1)" v-model="sendMsg.geti[1]"></el-input>
					 </el-col>
			 		 <el-col :span="4">
			 		 	<el-input placeholder="请输入内容" @change="checkNlInp(1)" v-model="sendMsg.nuli[1]"></el-input>
			 		 </el-col>
			 		 <el-col :span="4">
			 		 	<el-radio v-model="sendMsg.xingge[1]" :label="1.1" @change="xgcheck(1, 1.1)">+</el-radio>
  						<el-radio v-model="sendMsg.xingge[1]" :label="0.9" @change="xgcheck(1, 0.9)">-</el-radio>
			 		 </el-col>
			 		 <el-col :span="4">{{getMsg[1]}}</el-col>
				</el-row>	
			</div>
			<div class="row-item">
				<el-row :gutter="20">
			 		 <el-col :span="4">物防</el-col>
			 		 <el-col :span="4">{{info.def}}</el-col>
			 		 <el-col :span="4">
			 		 	<el-input placeholder="请输入内容" @change="checkGtInp(2)" v-model="sendMsg.geti[2]"></el-input>
					 </el-col>
			 		 <el-col :span="4">
			 		 	<el-input placeholder="请输入内容" @change="checkNlInp(2)" v-model="sendMsg.nuli[2]"></el-input>
			 		 </el-col>
			 		 <el-col :span="4">
			 		 	<el-radio v-model="sendMsg.xingge[2]" :label="1.1" @change="xgcheck(2, 1.1)">+</el-radio>
  						<el-radio v-model="sendMsg.xingge[2]" :label="0.9" @change="xgcheck(2, 0.9)">-</el-radio>
			 		 </el-col>
			 		 <el-col :span="4">{{getMsg[2]}}</el-col>
				</el-row>	
			</div>
			<div class="row-item">
				<el-row :gutter="20">
			 		 <el-col :span="4">特攻</el-col>
			 		 <el-col :span="4">{{info.spk}}</el-col>
			 		 <el-col :span="4">
			 		 	<el-input placeholder="请输入内容" @change="checkGtInp(3)" v-model="sendMsg.geti[3]"></el-input>
					 </el-col>
			 		 <el-col :span="4">
			 		 	<el-input placeholder="请输入内容" @change="checkNlInp(3)" v-model="sendMsg.nuli[3]"></el-input>
			 		 </el-col>
			 		 <el-col :span="4">
			 		 	<el-radio v-model="sendMsg.xingge[3]" :label="1.1" @change="xgcheck(3, 1.1)">+</el-radio>
  						<el-radio v-model="sendMsg.xingge[3]" :label="0.9" @change="xgcheck(3, 0.9)">-</el-radio>
			 		 </el-col>
			 		 <el-col :span="4">{{getMsg[3]}}</el-col>
				</el-row>	
			</div>
			<div class="row-item">
				<el-row :gutter="20">
			 		 <el-col :span="4">特防</el-col>
			 		 <el-col :span="4">{{info.spf}}</el-col>
			 		 <el-col :span="4">
			 		 	<el-input placeholder="请输入内容" @change="checkGtInp(4)" v-model="sendMsg.geti[4]"></el-input>
					 </el-col>
			 		 <el-col :span="4">
			 		 	<el-input placeholder="请输入内容" @change="checkNlInp(4)" v-model="sendMsg.nuli[4]"></el-input>
			 		 </el-col>
			 		 <el-col :span="4">
			 		 	<el-radio v-model="sendMsg.xingge[4]" :label="1.1" @change="xgcheck(4, 1.1)">+</el-radio>
  						<el-radio v-model="sendMsg.xingge[4]" :label="0.9" @change="xgcheck(4, 0.9)">-</el-radio>
			 		 </el-col>
			 		 <el-col :span="4">{{getMsg[4]}}</el-col>
				</el-row>	
			</div>
			<div class="row-item">
				<el-row :gutter="20">
			 		 <el-col :span="4">速度</el-col>
			 		 <el-col :span="4">{{info.spd}}</el-col>
			 		 <el-col :span="4">
			 		 	<el-input placeholder="请输入内容" @change="checkGtInp(5)" v-model="sendMsg.geti[5]"></el-input>
					 </el-col>
			 		 <el-col :span="4">
			 		 	<el-input placeholder="请输入内容" @change="checkNlInp(5)" v-model="sendMsg.nuli[5]"></el-input>
			 		 </el-col>
			 		 <el-col :span="4">
			 		 	<el-radio v-model="sendMsg.xingge[5]" :label="1.1" @change="xgcheck(5, 1.1)">+</el-radio>
  						<el-radio v-model="sendMsg.xingge[5]" :label="0.9" @change="xgcheck(5, 0.9)">-</el-radio>
			 		 </el-col>
			 		 <el-col :span="4">{{getMsg[5]}}</el-col>
				</el-row>	
			</div>
			<div class="row-item">
				<el-row :gutter="20">
			 		 <el-col :span="4">等级</el-col>
			 		 <el-col :span="4">
			 		 	<el-input placeholder="请输入内容" v-model="sendMsg.level" @change="checklv"></el-input>
			 		 </el-col>
			 		 <el-col :span="4"><el-button @click="all31" type="primary">全31</el-button></el-col>
			 		 <el-col :span="4"><el-button @click="resetnl" type="primary">清空</el-button></el-col>
			 		 <el-col :span="4"><el-button @click="send" type="success">选择</el-button></el-col>
			 		 <el-col :span="4">-</el-col>
				</el-row>	
			</div>
		</div>
	</div>
</template>
<style lang="scss">
	* {
		margin: 0;
		padding: 0;
	}
	.pokemon {
		background-color: green;
		padding: 20px;
		width: 980px;
		margin: 0 auto;
		.show {
			background-color: white;
			padding: 20px;
			.img {
				width: 80%;
				margin: 0 auto;
				img {
					max-width: 100%;
					max-height: 100%;
				}
			}
			.pokemon-name {
				padding: 9.5px 20px;
				text-align: right;
				border-right: 1px solid black;
				span {
					color: gray;
				}
			}
			

		}
		.compute {
			text-align: center;
			background-color: rgba(255, 255, 255, .5);
			.row-item {
				background-color: white;
				.el-col {
					height: 42px;
					line-height: 42px;
				}
			}
			
		}
	}
	
</style>
<script>
export default {
	data() {
		return {
			list: [
				{
					id: 1,
					name: "妙蛙种子",			
				},
				{
					id: 2,
					name: "妙蛙草",
				}
			],
			id: 1,
			info: {
					id: 1,
					name: "妙蛙种子",
					imgPath: "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547475872995&di=eeb43ef5dc40d884b5cdcb0c57edae42&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F6c92c25e4ad36977a79c758630826da89ddce8b1.jpg",
					hp: 45,
					atk: 49,
					def: 49,
					spk: 65,
					spf: 65,
					spd: 45
			},
			sendMsg: {
				level: 1,
				geti: [0,0,0,0,0,0],
				nuli: [0,0,0,0,0,0],
				xingge: [1,1,1,1,1,1]
			},
			getMsg: [0,0,0,0,0,0],
			xglock: [false, false]

		}
	},
	methods: {
		getInfo() {
			this.$http
					.get("/get/pokemon?name=" + this.name)
					.then(({ data }) => this.info = data.data)
		},
		all31() {
			this.sendMsg.geti = new Array(6).fill(31);
		},
		send() {
			let sendMsg = this.sendMsg;
			if(sendMsg.reduce((a, b) => + a + 0 + b) != 6){
				alert("性格选择不完整");
				return;
			}
			this.$http
				.post("/get/status", sendMsg)
				.then(({ data }) => {this.getMsg = data;})
		},
		xgcheck(index, value) {
			let arr = this.sendMsg.xingge.map(v => v === value? 1 : v);
			arr[index] = value;
			this.sendMsg.xingge = arr;
			// console.log(this.sendMsg.xingge);
		},
		resetxg() {
			this.sendMsg.xingge = new Array(6).fill(1);
		},
		checkNlInp(i) {
			let arr = this.sendMsg.nuli;
			let value = arr[i];
			if(value <= 0){
				value = 0;
			}
			if(value >= 252){
				value = 252;
			}
			arr[i] = value;
			let total = arr.reduce((a, b) => + a + 0 + b);
			console.log(arr, value, total);
			if(total >= 510){
				value += 510 - total;
			}
			this.$set(this.sendMsg.nuli, i, value);
		},
		checkGtInp(i) {
			let value = this.sendMsg.geti[i];
			if(value <= 0){
				value = 0;
			}
			if(value >= 31){
				value = 31;
			}
			this.$set(this.sendMsg.geti, i, value);
		},
		checklv() {
			let lvl = this.sendMsg.level;
			if(lvl <= 1){
				lvl = 1;
			}
			if(lvl >= 100){
				lvl = 100;
			}
			this.$set(this.sendMsg, "level", lvl);
			// console.log(this.sendMsg.level)
		},
		resetnl() {
			this.sendMsg.nuli = new Array(6).fill(0);
		}

	},
	beforeCreate() {
		this.$http
				.get("/get/all")
				.then(({ data }) => {this.list = data.data;})
	}
} 
</script>