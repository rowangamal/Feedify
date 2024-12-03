import  SearchBar  from './SearchBar';
import  FeedTabs  from './FeedTabs';
import  PostCard from './PostCard';
import '../../styles/Feed.css';

function Feed() {
  const posts = [
    {
      username: 'UserName',
      avatar: 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?auto=format&fit=crop&w=100&h=100&q=80',
      content:
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud',
      timestamp: '22:59',
    },
    {
      username: 'UserName',
      avatar: 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?auto=format&fit=crop&w=100&h=100&q=80',
      content:
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud',
      timestamp: '22:59',
    },
  ];

  return (
    <div className="feed">
      <SearchBar />
      <FeedTabs />
      <div className="posts">
        {posts.map((post, index) => (
          <PostCard key={index} {...post} />
        ))}
      </div>
    </div>
  );
}

export default Feed;