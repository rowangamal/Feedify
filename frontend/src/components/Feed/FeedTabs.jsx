function FeedTabs({ onTabChange, activeTab }) {
  return (
    <div className="feed-tabs">
      <button
        className={`tab-button ${activeTab === 'for-you' ? 'active' : ''}`}
        onClick={() => onTabChange('for-you')}
      >For You
      </button>

      <button
        className={`tab-button ${activeTab === 'following' ? 'active' : ''}`}
        onClick={() => onTabChange('following')}
      >
        Following
      </button>
    </div>
  );
}

export default FeedTabs;