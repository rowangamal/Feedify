function SearchBar() {
  return (
    <div className="search-bar">
      <div className="search-input-wrapper">
        <svg
          className="search-icon"
          width="20"
          height="20"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          strokeWidth="2"
          strokeLinecap="round"
          strokeLinejoin="round"
        >
          <circle cx="11" cy="11" r="8"></circle>
          <path d="m21 21-4.3-4.3"></path>
        </svg>
        <input
          type="text"
          placeholder="Article name or keywords..."
          className="search-input"
        />
      </div>
      <select className="category-select">
        <option>All categories</option>
      </select>
      <button className="search-button">Search</button>
    </div>
  );
}

export default SearchBar;