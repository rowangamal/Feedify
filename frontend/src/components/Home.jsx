
import Sidebar from './Sidebar/Sidebar';
import Feed from './Feed/Feed';
import '../styles/Home.css';
import { useState } from 'react';
function Home() {
    const [currentSection, setCurrentSection] = useState("Home")
return (
    <div className="home">
        <Sidebar setSection={setCurrentSection} />
        {currentSection == "Home"?
        <Feed />:
        <></>
        // currentSection == "Profile"?
        // <
        }
    </div>
);
}

export default Home;