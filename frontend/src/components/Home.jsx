
import Sidebar from './Sidebar/Sidebar';
import Feed from './Feed/Feed';
import '../styles/Home.css';
// import { useState } from 'react';
function Home() {
    
return (
    <div className="home">
        <Sidebar/>
        
        <Feed />
        
        
    </div>
);
}

export default Home;