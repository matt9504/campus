<template>
  <div clas="tabbarFrame d-flex">
    <div class="wrapper">
      <div class="tabbar pt-4">
        <!-- <div><img class="logos" src="@/assets/images/logo3.png" alt="" /></div> -->
        <router-link :to="{ name: 'Mainpage' }"
          ><i class="bi bi-house-door fs-2"></i
        ></router-link>
        <!-- <a class="nav-link" aria-current="page" href="#"
          ><i class="bi bi-house-door fs-2"></i
        ></a> -->
        <router-link :to="{ name: 'Mainpage' }"
          ><i class="bi bi-house-door fs-2"></i
        ></router-link>
        <a class="nav-link" aria-current="page" href="/sns">
          <i class="bi bi-journal-richtext fs-2"> </i>
        </a>
        <a class="nav-link" aria-current="page" href="/campsite">
          <i class="bi bi-binoculars fs-2 ms-4"> </i
        ></a>
        <!-- <img class="logos" src="@/assets/images/텐트_검정버전.png" alt="" /> -->
        <div v-if="this.$store.state.userEmail">
          <a class="nav-link" aria-current="page" href="/login"
            ><i class="bi bi-person fs-2"> </i>
          </a>
          <!-- <i class="fas fa-cog icon-settings" style="margin-right: 70px"></i> -->
        </div>
        <div v-else>
          <a
            class="nav-link"
            aria-current="page"
            href="
              profile/:userEmail"
          >
            <i class="bi bi-person fs-2"> </i>
          </a>
          <!-- <i class="fas fa-cog icon-settings" style="margin-right: 70px"></i> -->
        </div>

        <div class="circle">
          <i class="fas fa-plus plus-icon"></i>
          <i style="color: white" class="bi bi-person-plus social"> </i>
          <i class="bi bi-people social" @click="goMatematch()"> </i>
        </div>
        <div class="circleBackground"></div>
      </div>
    </div>
  </div>
  <!-- </div> -->
</template>

<script>
const tabs = document.querySelectorAll(".tab");

tabs.forEach((clickedTab) => {
  clickedTab.addEventListener("click", () => {
    tabs.forEach((tab) => {
      tab.classList.remove("active");
    });
    clickedTab.classList.add("active");
  });
});

export default {
  name: "Newmodal",
  components: {
    // Modal2,
  },
  mathods: {
    goMatematch() {
      console.log(this.$store.state.myNum);
      this.router.push({
        name: "Matematch",
        params: { userNo: this.$store.state.myNum },
      });
    },
  },
};
</script>

<style lang="scss" scoped>
$tabbar-height: 50px;
$circle-radius: 80px;
$backgroundColor: black;

.logos {
  width: 30x;
  height: 30px;
}
html,
body {
  padding: 0;
  margin: 0;
  height: 100%;
  width: 100%;
}
@media (min-width: 768px) {
  * {
    display: none;
  }
}
* {
  box-sizing: border-box;
}

.wrapper {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: $backgroundColor;
}

.tabbar {
  // position: relative;
  position: fixed;
  bottom: 0px;
  left: 0vw;
  height: $tabbar-height;
  // min-width: 300px;
  width: 100%;
  padding-left: 35px;
  padding-right: 35px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  // border-radius: 25px;
  background-color: white;
  box-shadow: 0 10px 20px rgba(82, 74, 74, 0.3);
  z-index: 10001;
}

.tabbar:after {
  content: "";
  position: absolute;
  bottom: 7px;
  height: 4px;
  width: 35%;
  left: 50%;
  transform: translateX(-50%);
  background-color: gray;
  opacity: 0.3;
}

.circle {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  bottom: calc(100px - #{$circle-radius / 2});
  margin-bottom: -50px;
  height: 45px;
  width: 45px;
  border-radius: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  background-color: #7ac4e1;
  z-index: 9;
  box-shadow: 0 5px 5px rgba(255, 147, 85, 0.35);
  transition: height 0.3s;
}

.circle:hover {
  height: 110px;
  border-radius: 50px;
}

.circle .plus-icon {
  color: white;
  font-size: 27px;
  transition: opacity 0.3s, transform 0.2s;
}

.circle:hover .plus-icon {
  transform: rotate(360deg);
  opacity: 0;
}

.circle .social {
  position: absolute;
  top: 195px;
  color: white;
  background-color: rgba(255, 212, 212, 0.308);
  height: 35px;
  width: 35px;
  margin-bottom: 5px;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 23px;
  cursor: pointer;
}

.circle .social:nth-child(2) {
  opacity: 0;
  transition: opacity 0.3s, top 0.5s ease;
}

.circle .social:nth-child(3) {
  opacity: 0;
  transition: opacity 0.3s, top 0.5s 0.1s ease;
}

.circle .social:nth-child(4) {
  opacity: 0;
  transition: opacity 0.3s, top 0.5s 0.22s ease;
  margin-bottom: 0px;
}

.circle:hover .social:nth-child(2) {
  opacity: 1;
  top: 5px;
}

.circle:hover .social:nth-child(3) {
  opacity: 1;
  top: calc(5px + 60px + 5px);
}

.circle:hover .social:nth-child(4) {
  opacity: 1;
  top: calc(5px + 60px + 5px + 60px + 5px);
}

.circleBackground {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  bottom: calc(100px - #{$circle-radius + 20} / 2);
  margin-bottom: 0;
  // height: calc(#{$circle-radius} + 20px);
  // width: calc(#{$circle-radius} + 20px);
  border-radius: calc(#{$circle-radius + 20} / 2);
  background-color: $backgroundColor;
}

.icon-home,
.icon-settings {
  font-size: 23px;
  color: $backgroundColor;
}
.bi-journal-richtext {
  font-size: 23px;
  color: $backgroundColor;
}
</style>
