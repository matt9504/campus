<template>
	<Navbar></Navbar>
	<div>
		
		<section class="profile-container">
			<section class="profile-grid">
				<div class="photo">
					<!-- <img src="https://w.namu.la/s/dabd214cce31238efc33e14536a8145b7ca02b2871bbc5c0b1213e26b3df4b69c33c94d815fc7529c0e9f8f05a39698b6b9b2f86785782c8825888b1f1403d6797e2d5a185e51eaf077a8e48185b69666b2ea7b1600935774217a7d9c45fa7faa3d0760690695013391b253d02840690" alt="이미지"> -->
					<img v-if="this.user_data.userProfileImage" v-bind:src="this.user_data.userProfileImage" alt="">
					<img v-if="this.user_data.userProfileImage == null" src="http://reflecteen.org.uk/wp-content/uploads/2017/10/person-holding-1-300x300.jpg">
				</div>
				<div class="name-buttons d-flex justify-content-between">
					<h1>{{ this.user_data.userNickname }}의 프로필</h1>
					
					<div class="">
						<button v-if="this.$route.params.userEmail === this.myEmail" @click="moveToModify" class="btn profile-settings-btn" aria-label="profile settings"><i class="fas fa-cog" aria-hidden="true"></i></button>
					</div>
					
				</div>
				
				<div class="mt-2">
					유저 평점: {{this.user_data.userRatePoint}} / 5
				</div>

				<div class="mt-2">
					<span class="mx-1" v-for="(value, idx) in campstylelist" :key="idx">
						#{{ value }}</span>
				</div>


				<div class="userInfo-follow" v-if="this.UserNo !== this.myId">
					<button v-if="isFollow === true" class="follow-buttonOn" type="button" @click="unfollow(); followcnt()">Unfollow</button>
					<button v-else class="follow-buttonOff" type="button" @click="follow(); followcnt()" >Follow</button>
					<!-- <button class="follow-btn" v-if="isFollow" @click="unfollow">언팔로우</button>
					<button class="follow-btn " v-else @click="follow">팔로우</button> -->
        </div>
      </section>
    </section>

    <!-- 프로필 정보 -->
    <section align="center">
      <ul class="profile-stats">
        <li>
          <div @click="currentmove()">
            <b>여정</b>
            <p>{{ this.historyList.length }}</p>
          </div>
        </li>
        <li>
          <div @click="currentmove2()">
            <b>게시글</b>
            <p>{{ this.feedList.length }}</p>
          </div>
        </li>

        <li>
          <!-- <div data-bs-toggle="modal" data-bs-target="#staticBackdrop"> -->
          <div data-bs-toggle="modal" data-bs-target="#staticBackdrop" @click="this.followlist()">
            <b>팔로워</b>
            <p>{{ this.followers }}</p>
          </div>
          <div
            class="modal fade"
            id="staticBackdrop"
            data-backdrop="static"
            data-keyboard="false"
            tabindex="-1"
            aria-labelledby="staticBackdropLabel"
            aria-hidden="true"
          >
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-body">
                  <followerModal :followerlist="this.followersList" />
                </div>
              </div>
            </div>
          </div>
        </li>

        <li>
          <div data-bs-toggle="modal" data-bs-target="#staticBackdrop2">
            <b>팔로우</b>
            <p>{{ this.followings }}</p>
          </div>
          <div
            class="modal fade"
            id="staticBackdrop2"
            data-backdrop="static"
            data-keyboard="false"
            tabindex="-1"
            aria-labelledby="staticBackdropLabel"
            aria-hidden="true"
          >
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-body">
                  <followingModal :followinglist="this.followingsList" />
                </div>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </section>

		<section class="instagram-container" style="width:100%">
			<footer class="image-footer">
				<!-- <div id="tabs-posts"> -->
					<div v-for="(tab, index) in tabs" :key="index"
							v-bind:class="{active: currentTab === index}"
							v-on:click="currentTab = index">{{ tab }}</div>			
				<!-- </div>	 -->
			</footer>
		</section>

		<section class="mt-3 d-flex justify-content-center">
			<div class="tab-content">
				<div v-show="currentTab==0">
					<ProfileInfo
						:user_data="this.user_data"
						:camp_List="this.campList"
					/>
				</div>
				<div v-show="currentTab==1">
					<ProfileFeed
						:feedList="this.feedList"
					/>
				</div>
				<div v-show="currentTab==2">
					<ProfileHistory
						:historyList="this.historyList"
					/>
				</div>
			</div>
		</section>

		

	</div>
</template>

<script>
import axios from "axios";
// import FollowModal from '@/components/user/FollowModal.vue'
import ProfileFeed from "@/components/user/Profilefeed.vue";
import ProfileHistory from "@/components/user/Profilehistory.vue";
import ProfileInfo from "@/components/user/ProfileInfo.vue";
import followerModal from "@/components/user/followerModal.vue";
import followingModal from "@/components/user/followingModal.vue";
import Navbar from '@/components/common/Navbar.vue'
import Swal from "sweetalert2";
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: "Profile",
  props: {
    userEmail: {
      type: String,
    },
  },

  data() {
    return {
      // userId: Number(this.$route.params.user_id), // 현재 유저넘버
      my_data: this.$store.state.userList,
      myId: this.$store.state.myNum, // 로그인한 유저넘버
      myEmail: this.$store.state.userEmail,
      user_data: {},
      userId: "", // 보고있는 프로필 넘버
      currentTab: 0,
      tabs: ["Profile", "FEED", "History"],
      UserNo: "",

      followers: 0,
      followings: 0,
      isFollow: false,

      historyList: [],
      feedList: [],
      campList: [],

      campstylelist: [],

      followersList: [], // userId 팔로워 리스트
      followingsList: [], // userId 팔로잉 리스트
    };
  },
  components: {
    // FollowModal,
    ProfileFeed,
    ProfileHistory,
    ProfileInfo,
    followerModal,
    followingModal,
    Navbar,
  },

  created: function () {
    console.log("보고있는 프로필", this.$route.params.userEmail);
    console.log("로그인", this.myEmail, this.myId);
    // console.log("데이터",this.user_data)
    // console.log("세부", this.user_data.campEquipTent)
    // 보고있는 이메일
    this.user_email = this.$route.params.userEmail; // 이메일에서 바꿔야함 유저넘버로
    console.log("보내기전", this.user_email);
    this.getProfile(); // 유저 정보 가져오기
  },

  methods: {
    currentmove: function () {
      this.currentTab = 2;
    },
    currentmove2: function () {
      this.currentTab = 1;
    },
    getProfile: function () {
      axios
        .get(`${SERVER_URL}/user/${this.user_email}`)
        .then((res) => {
          // 여기서 불러온 아이디 넘버 가져오기
          this.user_data = res.data;
          this.UserNo = res.data.userNo;
          console.log("일단가져와라", this.UserNo);
          this.getMate(); // 메이트 가져오기
          this.getSns();
          this.followcnt();
          this.followlist();
          this.followinglist();
          this.styleget();
          this.campget();
        })
        .catch(() => {
          console.log("프로필을 가져올 수 없습니다.");
        });
    },

    // 음식/음주, 불명/수다, 캠프파이어, 등산, 사진찍기, 음악감상
    styleget: function () {
      if (this.user_data.campStyle1 === "Y") {
        this.campstylelist.push("음식/음주");
      }
      if (this.user_data.campStyle2 === "Y") {
        this.campstylelist.push("불멍/수다");
      }
      if (this.user_data.campStyle3 === "Y") {
        this.campstylelist.push("캠프파이어");
      }
      if (this.user_data.campStyle4 === "Y") {
        this.campstylelist.push("등산");
      }
      if (this.user_data.campStyle5 === "Y") {
        this.campstylelist.push("사진찍기");
      }
      if (this.user_data.campStyle6 === "Y") {
        this.campstylelist.push("음악감상");
      }
      console.log(this.campstylelist);
    },
    campget: function () {
      axios.get(`${SERVER_URL}/camp/like/${this.UserNo}`).then((res) => {
        this.campList = res.data.campLikeList;
        console.log("가나다", res);
        console.log("흐흐", this.campList);
      });
    },

    getMate: function () {
      axios.get(`${SERVER_URL}/mate`).then((res) => {
        console.log("메이트", res);
        for (let i = 0; i < res.data.count; i++) {
          if (res.data.list[i].userNo === this.UserNo) {
            this.historyList.push(res.data.list[i]);
          }
        }
      });
    },
    getSns: function () {
      axios.get(`${SERVER_URL}/sns/feed/${this.UserNo}`).then((res) => {
        for (let i = 0; i < res.data.list.length; i++) {
          if (res.data.list[i].userNo === this.UserNo) {
            this.feedList.push(res.data.list[i]);
          }
        }
      });
    },
    follow: function () {
      console.log("뭐야", this.myId);
      axios
        // console.log(follow_data)
        .post(`${SERVER_URL}/follow/${this.myId}/${this.UserNo}`, {
          userNo: this.myId,
          followUserNo: this.UserNo,
        })
        .then((res) => {
          console.log(res)
          this.isFollow = true;
          this.followers += 1;
          // this.$router.go()
          console.log(this.followersList)
          // this.followersList.push({'userEmail':this.$store.state.userList.userEmail, 'userNickname':this.$store.state.userList.userNickname})
          console.log("흠", this.followersList)
        })
        .catch((err) => {
          console.log(err);
        });
    },
    unfollow: function () {
      axios
        // console.log(follow_data)
        .delete(`${SERVER_URL}/follow/${this.myId}/${this.UserNo}`, {
          userNo: this.myId,
          followUserNo: this.UserNo,
        })
        .then((res) => {
          this.isFollow = false;
          this.followers -= 1;
          // this.$router.go()
          console.log("언팔로우", res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    followcnt: function () {
      axios.get(`${SERVER_URL}/follow/num/${this.UserNo}`).then((res) => {
        console.log("이게", res);
        this.followers = res.data.followNum;
        this.followings = res.data.followerNum;
      });
    },
    followlist: function () {
      axios.get(`${SERVER_URL}/follow/${this.UserNo}`).then((res) => {
        // console.log("보자",res.data.follow.length)
        // for (let i=0; i<res.data.follow.length; i++) {
        // 	this.followersList.push(res.data.follow)
        // }
        this.followersList = res.data.follow;
        for (let i = 0; i < res.data.follow.length; i++) {
          if (res.data.follow[i].userEmail === this.myEmail) {
            this.isFollow = true;
          }
        }
      });
    },
    followinglist: function () {
      axios.get(`${SERVER_URL}/follower/${this.UserNo}`).then((res) => {
        console.log("확인2", res.data);
        this.followingsList = res.data.follower;
        // for (let i=0; i<res.data.follower.length; i++) {
        // 	if (res.data.follower[i].userEmail === this.myEmail) {
        // 		this.followingsList.push(res.data.follow[i])
        // 	}
        // }
      });
    },

    logout: function () {
      this.$store.state.user = null;
      this.$store.state.userEmail = null;
      this.$store.state.userGender = null;
      this.$store.state.SearchWord = null;
      this.$store.dispatch("logout");
      sessionStorage.removeItem("userList");
      sessionStorage.removeItem("myNum");
      sessionStorage.removeItem("userEmail");
      sessionStorage.removeItem("userPassword");
      localStorage.removeItem("vuex");
      Swal.fire({
        title: "로그아웃 되었습니다.",
        icon: "success",
        timer: 2000,
      });
      this.$router.push({ name: "Login" });
    },

    moveToModify: function () {
      this.$router.push({ name: "Modify" });
    },
  },
  computed: {},
};
</script>

<style scoped>
.profile-container {
  display: flex;
  justify-content: center;
  margin-bottom: 15px;
  padding-top: 30px;
  font-family: "Roboto", sans-serif;
  font-weight: 300;
}
.profile-grid {
  display: grid;
  grid-template-columns: minmax(150px, 230px) minmax(auto, 700px);
  grid-template-rows: 30px auto auto 30px auto;
  grid-template-areas:
    "photo  user-bottom"
    "photo  second-line"
    "photo  description"
    "photo  followers-line";
  /* "h-post h-post";    */
  row-gap: 10px;
}
.profile-grid div {
  display: flex;
}
.profile-grid .photo {
  padding-left: 20px;
  grid-area: photo;
}
.profile-grid .photo img {
  width: 140px;
  height: 140px;
  border-radius: 50%;
  padding: 3px;
  background-color: white;
  border: 3px solid rgb(192, 187, 187);
  object-fit: cover;
}
.profile-grid .name-buttons {
  grid-area: user-bottom;
  display: flex;
}
.profile-grid .name-buttons div {
  height: 40px;
  width: 40px;
}
.profile-grid .name-buttons button {
  display: flex;
  width: 40px;
  height: 40px;
  font-weight: 600;
  padding: 0 5px;
  align-items: center;
  justify-content: center;
  border: 2px solid #efefef;
  background-color: rgb(250, 250, 250);
}

.profile-stats {
  margin-top: 2.3rem;
  align-items: center;
  /* border-bottom: 0.1rem solid #dadada; */
}

.profile-stats li {
  display: inline-block;
  font-size: 1.5rem;
  line-height: 1.5;
  margin-right: 3.5rem;
  cursor: pointer;
}

.profile-stats li:last-of-type {
  margin-right: 0;
}

.instagram-container {
  display: grid;
  justify-content: center;
  grid-template-columns: repeat(3, minmax(auto, 293px));
  grid-template-rows: auto;

  row-gap: 25px;
  column-gap: 25px;
}
.instagram-container .image-footer {
  border-top: 1px solid rgb(201, 195, 195);
  align-items: center;
  display: flex;
  justify-content: center;
  grid-column-start: 1;
  grid-column-end: 4;
  background-color: #fafafa;
}
.instagram-container .image-footer div {
  display: flex;
  padding-top: 15px;
  margin-left: 50px;
  margin-right: 50px;
  justify-content: center;
  font-size: 1.2rem;
  font-family: "Roboto", sans-serif;
  align-items: center;
  font-weight: 300;
  text-align: center;
}
.instagram-container .image-footer div {
  text-decoration: none;
  color: rgb(90, 90, 90);
}
.instagram-container .post {
  cursor: pointer;
  position: relative;
  display: block;
  width: 100%;
}
.img-photo {
  width: 100%;
  height: 100%;
  object-fit: cover;
  vertical-align: top;
}
.instagram-container .hover-img {
  background: rgba(0, 0, 0, 0.4);
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;
  top: 0;
  display: none;
  align-items: center;
  justify-content: center;
  color: white;
  text-align: center;
  font-size: 2.2rem;
}

.follow-buttonOn {
  border-radius: 10px;
  background-color: grey;
  font-size: 18px;
  color: white;
}
.follow-buttonOff {
  border-radius: 10px;
  background-color: blue;
  font-size: 18px;
  color: white;
}

@media (max-width: 740px)
{ 
		.profile-stats {
		margin-top: 2.3rem;
		align-items: center;
		/* border-bottom: 0.1rem solid #dadada; */
	}

	.profile-stats li {
		display: inline-block;
		font-size: 1.0rem;
		line-height: 1.5;
		margin-right: 3.5rem;
		cursor: pointer;
	}

	.profile-stats li:last-of-type {
		margin-right: 0;
	}
	.profile-container{
	
		justify-content: left;
		margin-bottom: 20px;
		padding-top: 30px;
		font-family: 'Roboto',sans-serif;
		font-weight: 300;
	}
	.profile-grid
	{
		/*grid-template-columns:90px auto;*/
		grid-template-columns: 20vw 80vw;
		grid-template-rows: auto auto;
		grid-template-areas: "photo  user-bottom"
													"description  description"
													"followers-line  followers-line"
													"h-post h-post"
													"second-line second-line";
													
		row-gap: 12px;
	}
	.profile-grid .photo{
			padding-left: 8px;
			width: 100px;
			height: auto;
	}

	
.profile-grid .photo img{
	width: 80px;
	height: 80px;
	padding: 2px;
	border: 2px solid rgb(192, 187, 187);;
}
	.profile-grid .name-buttons{
		grid-area: user-bottom;
		display: block;
		padding-left: 10px;

	}
	.profile-grid .name-buttons div:nth-of-type(1){
		margin-bottom: 9px;
	}
	.profile-grid .name-buttons div:nth-of-type(2){
		padding-left: 15px;
	}
	.instagram-container{
		display: grid;
		justify-content: center;
		grid-template-columns:repeat(3,minmax(auto,293px));
		grid-template-rows: auto ;
		row-gap: 3px;
		column-gap: 3px;
	}
	.instagram-container .image-footer {
		width: 100%;
		border-top: 1px solid rgb(201, 195, 195);
		align-items: center;
		display: flex;
		justify-content: center;
		grid-column-start: 1;
		grid-column-end: 4;
		background-color: #fafafa;
}
.instagram-container .image-footer div {
  display: flex;
  padding-top: 15px;
  margin-left: 50px;
  margin-right: 50px;
  justify-content: center;
  font-size: 1.2rem;
  font-family: "Roboto", sans-serif;
  align-items: center;
  font-weight: 300;
  text-align: center;
}

	/* .instagram-container .image-footer div{
		padding-top: 8px;
		padding-bottom: 8px;
	} */
	.footer-end{
		height: 50px; 
		font-size: 1.3rem;
	}
}

@media (max-width: 400px)
{
    header .search-container{
        display: none;
    }
    header .options-container{
        margin-left: auto;
    }
    .profile-grid{
        grid-template-columns: 25vw 75vw;
    }
    .footer-end{

        height: 40px; 
        font-size: 1.2rem;
    }
		.instagram-container .image-footer div {
			display: flex;
			padding-top: 15px;
			margin-left: 40px;
			margin-right: 40px;
			justify-content: center;
			font-size: 0.8rem;
			font-family: "Roboto", sans-serif;
			align-items: center;
			font-weight: 300;
			text-align: center;
		}
}
</style>
