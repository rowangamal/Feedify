
function SidebarLink({ icon: Icon, label,show,  active, setSection }) {
  return (
    <a href="#" className={`nav-link ${active ? 'active' : ''}`} onClick={()=> {
      setSection(label)
    }}>
      {Icon}
      <span>{label}</span>
    </a>
  );
}

export default SidebarLink;