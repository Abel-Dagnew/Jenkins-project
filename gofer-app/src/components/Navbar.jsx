import { Link, useLocation } from "react-router-dom";
import { useState } from "react";
import logo from "/LogoName.png"; // Update the path to your actual logo file

const Navbar = () => {
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);
  const location = useLocation();

  const toggleMobileMenu = () => {
    setIsMobileMenuOpen(!isMobileMenuOpen);
  };

  return (
    <nav className="bg-gray-50 shadow-md fixed w-full z-11">
      <div className="px-6 sm:px-8 lg:px-10 flex justify-between items-center h-26">
        {/* Logo Aligned to the Left Edge */}
        <Link to="/" className="ml-40">
          <img src={logo} alt="Gofer Technologies Logo" className="h-15 w-auto" />
        </Link>

        {/* Centered Menu Links (for Desktop) */}
        <div className="hidden md:flex items-center justify-center space-x-12 flex-grow">
          <Link to="/" className={`text-2xl font-semibold transition duration-200 ${location.pathname === '/' ? 'text-indigo-600' : 'text-gray-800 hover:text-indigo-600'}`}>Home</Link>
          <Link to="/services" className={`text-2xl font-semibold transition duration-200 ${location.pathname === '/services' ? 'text-indigo-600' : 'text-gray-800 hover:text-indigo-600'}`}>Services</Link>
          <Link to="/projects" className={`text-2xl font-semibold transition duration-200 ${location.pathname === '/projects' ? 'text-indigo-600' : 'text-gray-800 hover:text-indigo-600'}`}>Projects</Link>
          <Link to="/about" className={`text-2xl font-semibold transition duration-200 ${location.pathname === '/about' ? 'text-indigo-600' : 'text-gray-800 hover:text-indigo-600'}`}>About</Link>
          <Link to="/blogs" className={`text-2xl font-semibold transition duration-200 ${location.pathname === '/blogs' ? 'text-indigo-600' : 'text-gray-800 hover:text-indigo-600'}`}>Blogs</Link>
          <Link to="/testimonials" className={`text-2xl font-semibold transition duration-200 ${location.pathname === '/testimonials' ? 'text-indigo-600' : 'text-gray-800 hover:text-indigo-600'}`}>Testimonials</Link>
          <Link to="/contact" className={`text-2xl font-semibold transition duration-200 ${location.pathname === '/contact' ? 'text-indigo-600' : 'text-gray-800 hover:text-indigo-600'}`}>Contact</Link>
        </div>

        {/* Mobile Menu Button */}
        <div className="md:hidden flex items-center">
          <button onClick={toggleMobileMenu} className="text-gray-700 hover:text-blue-500 focus:outline-none">
            <svg xmlns="http://www.w3.org/2000/svg" className="h-8 w-8" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16M4 18h16" />
            </svg>
          </button>
        </div>
      </div>

      {/* Mobile Menu */}
      {isMobileMenuOpen && (
        <div className="md:hidden bg-white shadow-md rounded-lg mt-4">
          <div className="flex flex-col items-center space-y-6 py-6">
            <Link to="/" className={`text-xl font-semibold transition duration-200 ${location.pathname === '/' ? 'text-indigo-600' : 'text-gray-800 hover:text-indigo-600'}`} onClick={toggleMobileMenu}>Home</Link>
            <Link to="/services" className={`text-xl font-semibold transition duration-200 ${location.pathname === '/services' ? 'text-indigo-600' : 'text-gray-800 hover:text-indigo-600'}`} onClick={toggleMobileMenu}>Services</Link>
            <Link to="/projects" className={`text-xl font-semibold transition duration-200 ${location.pathname === '/projects' ? 'text-indigo-600' : 'text-gray-800 hover:text-indigo-600'}`} onClick={toggleMobileMenu}>Projects</Link>
            <Link to="/about" className={`text-xl font-semibold transition duration-200 ${location.pathname === '/about' ? 'text-indigo-600' : 'text-gray-800 hover:text-indigo-600'}`} onClick={toggleMobileMenu}>About</Link>
            <Link to="/blogs" className={`text-xl font-semibold transition duration-200 ${location.pathname === '/blogs' ? 'text-indigo-600' : 'text-gray-800 hover:text-indigo-600'}`} onClick={toggleMobileMenu}>Blogs</Link>
            <Link to="/testimonials" className={`text-xl font-semibold transition duration-200 ${location.pathname === '/testimonials' ? 'text-indigo-600' : 'text-gray-800 hover:text-indigo-600'}`} onClick={toggleMobileMenu}>Testimonials</Link>
            <Link to="/contact" className="bg-indigo-600 text-white text-2xl font-bold px-8 py-4 rounded-lg hover:bg-indigo-700 transition duration-200" onClick={toggleMobileMenu}>Contact</Link>
          </div>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
