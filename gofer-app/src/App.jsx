import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import Home from "./pages/Home";
import ServicePage from "./pages/Service";
// import Projects from "./pages/Projects";
import AboutPage from "./pages/About";
// import Blogs from "./pages/Blogs";
import TestimonialsPage from "./pages/Testimonials";
import ContactPage from "./pages/Contact";

function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/services" element={<ServicePage />} />
        {/* <Route path="/projects" element={<Projects />} /> */}
        <Route path="/about" element={<AboutPage />} />
        {/* <Route path="/blogs" element={<Blogs />} /> */}
        <Route path="/testimonials" element={<TestimonialsPage />} />
        <Route path="/contact" element={<ContactPage />} />
      </Routes>
      <Footer />
    </Router>
  );
}

export default App;
