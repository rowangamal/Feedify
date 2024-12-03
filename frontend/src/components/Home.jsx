
import Sidebar from './Sidebar/Sidebar';
import Feed from './Feed/Feed';
import '../styles/Home.css';
function Home() {
return (
    <div className="home">
        <Sidebar />
        <Feed />
    </div>
);
}

export default Home;