/* eslint-disable no-unused-vars */
import React from 'react';
import { Link } from 'react-router-dom';

const Hero = () => {
  return (
    <section className="hero bg-blue-900 relative text-white py-40 flex " style={{ height: '100vh' }}>
      <div className="overlay absolute inset-0 bg-black opacity-50"></div>
      <div className="container relative z-10 flex flex-col md:flex-row items-center w-full px-10 ml-50">
        {/* Left Side (Brand Name, Tagline, Description, Button) */}
        <div className="text-left w-full md:w-1/2 xl:w-1/2 space-y-6">
          <h1 className="brand-name text-5xl md:text-6xl font-extrabold animate__animated animate__fadeIn animate__delay-1s bg-clip-text text-transparent bg-gradient-to-r from-blue-500 to-purple-600">
            Gofer Technologies
          </h1>
          <h2 className="text-2xl md:text-4xl font-semibold animate__animated animate__fadeIn animate__delay-2s">
            Empowering Your Business with Technology
          </h2>
          <p className="mt-4 text-lg opacity-75 animate__animated animate__fadeIn animate__delay-3s">
            We specialize in Azure cloud solutions, DevOps expertise, and custom web design to help small businesses thrive in a digital world.
          </p>
          <Link to="/services">
            <button className="mt-6 px-6 py-3 bg-blue-600 text-white rounded shadow-lg hover:shadow-xl transition duration-300 transform hover:scale-105">
              Get Started
            </button>
          </Link>
        </div>

        {/* Right Side (Images in Triangle Shape) */}
        <div className="md:w-1/2 xl:w-1/2 mt-8 md:mt-0 flex justify-center relative">
          {/* Top Image */}
          <div className=" absolute top-0 transform -translate-x-1/2 left-1/2">
            <img src="cloud-img.png" alt="Cloud Solution" className="rounded-lg shadow-lg transform transition duration-300 hover:scale-105" />
          </div>
          {/* Bottom Left Image */}
          
          {/* Bottom Right Image */}
          <div className="absolute bottom-0 right-1/4 transform translate-x-1/2">
            <img src="cloud-img.png" alt="Web Design" className="rounded-lg shadow-lg transform transition duration-300 hover:scale-105" />
          </div>
        </div>
      </div>
      <div className="absolute bottom-10 left-1/2 transform -translate-x-1/2">
        <svg xmlns="http://www.w3.org/2000/svg" className="w-10 h-10 text-white animate-bounce" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
          <circle cx="12" cy="12" r="10" />
          <path d="M12 8v4l3 3" />
        </svg>
      </div>
    </section>
  );
};

export default Hero;
