// import CreatePost from "./components/CreatePost";
// import Home from "./components/Home";
import Profile from "./components/UserProfile/Profile";
function App() {
  return (
    <>
      <Profile userId={3} username={"Omar Aldawy"} followers={37} following={77}/>
      {/* <CreatePost /> */}
      {/* <Home /> */}
    </>
  );
}

export default App
