import { NavLink } from "react-router-dom";

function SidebarLink({ icon: Icon, label, to }) {
  return (
    <NavLink
      to={to}
      className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`}
    >
      {Icon}
      <span>{label}</span>
    </NavLink>
  );
}

export default SidebarLink;