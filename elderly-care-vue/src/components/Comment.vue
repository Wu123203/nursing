<template>
  <div class="hbl-fa">
    <!-- 评论发布 -->
    <div class="hbl-comm" v-if="user.role == 2">
      <div class="comment" style="margin-top:0">
        <el-input type="textarea" :autosize="{ minRows: 4, maxRows: 8}" placeholder="在此输入评论内容..." v-model="textareaMap[0]">
        </el-input>
        <div class="hbl-owo">
          <div class="publish publish-btn">
            <el-button type="primary" @click="doSend()">发送</el-button>
          </div>
        </div>
      </div>
    </div>

    <!-- 评论数量 -->
    <div class="comm">
      <div class="com-rep com-title">
        <el-icon>
          <ChatDotSquare />
        </el-icon> 评论
        <span class="com-span"> ({{commentList.length}})</span>
      </div>
    </div>

    <!-- 评论列表 -->
    <div class="xcp-item" v-for="(item,index) in commentList" :key="index">
      <div class="left">
        <div class="x-avatar">
          <div class="x-avatar-img" :style='{backgroundImage:"url(" + item.avatar + ")"}'></div>
        </div>
      </div>
      <div class="right">
        <div class="user-bar">
          <h5 class="user-bar-uname">{{item.nickname}}</h5>
        </div>
        <div class="x-interact-rich-text rich-text">
          <div class="cc cc-to" v-if="item.type == 1">
            <a href="#">@{{item.beReplyName}}</a>
          </div>
          <span class="type-text">{{item.content}}</span>
        </div>
        <div class="interact-bar">
          <div class="interact-bar-left">
            <span class="time"><el-icon>
                <Clock />
              </el-icon> {{item.createTime}}</span>
          </div>
          <div class="interact-bar-right" v-if="user.role == 2">
            <div class="reply" @click="doReply(item.id)">
              <el-icon>
                <ChatDotSquare />
              </el-icon> <span class="reply-text">回复</span>
            </div>
          </div>
        </div>
        <div class="comment" v-if="replyMap[item.id]">
          <el-input type="textarea" :autosize="{ minRows: 4, maxRows: 8}" :placeholder="'@'+item.nickname" v-model="textareaMap[item.id]">
          </el-input>
          <div class="hbl-owo">
            <div class="publish publish-btn">
              <el-button type="primary" @click="doChidSend(item.id,item.id)">发送</el-button>
            </div>
          </div>
        </div>

      </div>
    </div>
    <el-empty description="去说点什么吧" v-if="commentList == null || commentList.length==0"></el-empty>

  </div>
</template>
<script>
export default {
  props: {
    commentNum: {
      type: Number,
      default: 2
    },
    commentList: {
      type: Array,
      default: () => []
    },

  },
  data() {
    return {
      user: this.$store.state.user,
      avatar: require('@/assets/images/avatar.png'),
      replyMap: [],
      buttonMap: [],
      textareaMap: [],
    }
  },
  computed: {
    user() {
      return this.$store.state.user
    },
  },
  methods: { //事件处理器

    doSend() {
      this.$emit("doSend", this.textareaMap[0], 0);
      this.textareaMap[0] = ''
      // this.$set(this.textareaMap, 0, '')
    },

    doChidSend(index, pid) {
      this.$emit("doSend", this.textareaMap[index], pid);
      this.textareaMap[index] = ''
      // this.$set(this.textareaMap, index, '')
    },

    doReply(index) {
      this.replyMap[index] = !this.replyMap[index]
      // this.$set(this.replyMap, index, !this.replyMap[index])
    },

  },

}
</script>
<style type="text/css" scoped>
.comment {
  display: inline-block;
  vertical-align: top;
  width: 100%;
  margin-top: 10px;
}

.publish {
  margin-top: 10px;
  display: inline-block;
  vertical-align: top;
}

.tmsgBox {
  position: relative;
  background: #fff;
  padding: 15px;
  margin-bottom: 20px;
  border-radius: 5px;
}
.tmsg-respond h3 {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 8px;
}
.tmsg-respond h3 small {
  font-size: smaller;
  cursor: pointer;
}
.tmsg-respond textarea {
  background: #f4f6f7;
  height: 100px;
  margin-bottom: 10px;
}

/*用户输入表单*/
.tmsg-r-info {
  margin: 10px 0;
}
.tmsg-r-info input {
  height: 30px;
  border-radius: 4px;
  background: #f4f6f7;
}
.tmsg-r-info .info-submit {
  margin: 10px 0;
  text-align: center;
}
.tmsg-r-info .info-submit p,
.tmsg-commentshow h1 {
  /*background: #97dffd;*/
  color: #fff;
  border-radius: 5px;
  cursor: pointer;
  /*transition: all .3s ease-in-out;*/
  height: 30px;
  line-height: 30px;
  text-align: center;
}
/*评论列表*/
.tmsg-comments .tmsg-comments-tip {
  display: block;
  border-left: 2px solid #363d4c;
  padding: 0 10px;
  margin: 40px 0;
  font-size: 20px;
}
.tmsg-commentlist {
  margin-bottom: 20px;
}
.tmsg-commentshow > .tmsg-commentlist {
  border-bottom: 1px solid #e5eaed;
}
.tmsg-c-item {
  border-top: 1px solid #e5eaed;
}
.tmsg-c-item article {
  margin: 20px 0;
}
.tmsg-c-item article header {
  margin-bottom: 10px;
}
.tmsg-c-item article header img {
  width: 65px;
  height: 65px;
  border-radius: 50%;
  float: left;
  transition: all 0.4s ease-in-out;
  -webkit-transition: all 0.4s ease-in-out;
  margin-right: 15px;
  object-fit: cover;
}
.tmsg-c-item article header img:hover {
  transform: rotate(360deg);
  -webkit-transform: rotate(360deg);
}
.tmsg-c-item article header .i-name {
  font-size: 14px;
  margin: 5px 8px 7px 0;
  color: #444;
  font-weight: bold;
  display: inline-block;
}
.tmsg-c-item article header .i-class {
  display: inline-block;
  margin-left: 10px;
  background: #dff0d8;
  color: #3c763d;
  border-radius: 5px;
  padding: 3px 6px;
  font-size: 12px;
  font-weight: 400;
}
.tmsg-c-item article header .i-time {
  color: #aaa;
  font-size: 12px;
}
.tmsg-c-item article section {
  margin-left: 80px;
}
.tmsg-c-item article section p img {
  vertical-align: middle;
}
.tmsg-c-item article section .tmsg-replay {
  margin: 10px 0;
  font-size: 12px;
  color: #64609e;
  cursor: pointer;
}
.hbl-owo {
  text-align: left;
}
.comm {
  padding: 10px 0;
  border-bottom: 1px solid #eee;
}
.su {
  margin-top: 2px;
  width: 5px;
  height: 23px;
  background: #3cb371; /*#1E90FF*/
}
.com-rep {
  display: inline-block;
  vertical-align: top;
}
.com-title {
  margin-left: 5px;
}
.com-title i {
  vertical-align: text-top;
}
.com-span {
  font-size: 14px;
}
.hbl-fa {
  text-align: left;
}
.hbl-comm {
  margin-bottom: 20px;
}

.content {
  margin: 10px 0;
}
.comment-f {
  display: inline-block;
  vertical-align: middle;
}
.nickname {
  font-size: 14px;
}
.author {
  display: inline-block;
}
.icon {
  background: #dff0d8;
  color: #3c763d;
  border-radius: 5px;
  padding: 3px 6px;
  font-size: 12px;
  font-weight: 400px;
}
.date {
  font-size: 12px;
  margin-top: 5px;
  color: grey;
}
.reply-content {
  word-wrap: break-word;
  width: 100%;
  font-size: 14px;
  line-height: 25px;
  margin-left: 50px;
}

::v-deep .reply-content img {
  vertical-align: middle;
}

.reply-fa {
  margin-top: 5px;
}
.reply-font {
  margin-bottom: 5px;
  color: grey;
  cursor: pointer;
}
.reply-font i {
  font-size: 16px;
}
.children {
  padding-left: 40px;
}
.cc {
  display: inline-block;
}
.cc-to a {
  text-decoration: none;
  color: #409eff;
  margin-right: 4px;
}
.icon-reply {
  display: inline-block;
  vertical-align: top;
}

.icon-hf {
  font-size: 12px;
  margin-top: -1px;
}

/** 新样式 */
.xcp-item {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-orient: horizontal;
  -webkit-box-direction: normal;
  -ms-flex-direction: row;
  flex-direction: row;
  padding-top: 28px;
  font-size: 14px;
  color: #222;
}

.xcp-item .left {
  width: 64px;
}

.x-avatar {
  position: relative;
  display: block;
  width: 48px;
  height: 48px;
}

.x-avatar-img {
  position: relative;
  width: 100%;
  height: 100%;
  background-position: 50% 50%;
  background-repeat: no-repeat;
  background-size: cover;
  border-radius: 50%;
}

.x-avatar-img:after {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  width: 300%;
  height: 300%;
  border: 3px solid rgba(0, 0, 0, 0.1);
  border-radius: 100%;
  -webkit-transform: scale(0.333333);
  transform: scale(0.333333);
  -webkit-transform-origin: 0 0;
  transform-origin: 0 0;
}

.xcp-item .right {
  -webkit-box-flex: 1;
  -ms-flex: 1;
  flex: 1;
  width: 1%;
}

.user-bar {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-orient: horizontal;
  -webkit-box-direction: normal;
  -ms-flex-direction: row;
  flex-direction: row;
  -webkit-box-pack: start;
  -ms-flex-pack: start;
  justify-content: flex-start;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  font-family: PingFangSC-Regular;
  position: relative;
}

.user-bar-uname {
  font-family: PingFangSC-Semibold;
  font-size: 13px;
  color: #222;
}

.x-interact-rich-text {
  padding-top: 10px;
  font: 14px/22px PingFangSC-Regular;
  color: #222;
  text-align: justify;
  word-wrap: break-word;
  word-break: break-all;
  word-break: break-word;
}

::v-deep .x-interact-rich-text .type-text img {
  vertical-align: top;
}

.interact-bar {
  position: relative;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-pack: justify;
  -ms-flex-pack: justify;
  justify-content: space-between;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  -webkit-box-orient: horizontal;
  -webkit-box-direction: normal;
  -ms-flex-direction: row;
  flex-direction: row;
  padding-top: 8px;
  font-size: 12px;
  color: #9195a3;
}

.interact-bar > * {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}

.interact-bar-left {
  -webkit-box-pack: start;
  -ms-flex-pack: start;
  justify-content: flex-start;
}

.interact-bar-left span i {
  vertical-align: text-top;
}

.interact-bar-right {
  -webkit-box-pack: end;
  -ms-flex-pack: end;
  justify-content: flex-end;
  color: #222;
}

.interact-bar-right .reply {
  cursor: pointer;
}

.interact-bar-right .reply i {
  vertical-align: text-top;
}

.interact-bar-right .icon {
  width: 20px;
  height: 20px;
  margin-right: 3px;
}
</style>