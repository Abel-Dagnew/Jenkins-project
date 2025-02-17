/* eslint-disable no-unused-vars */
import React from 'react';

const Services = () => {
  return (
    <section className="services min-h-screen py-20 bg-gray-100 z-10 flex items-center">
      <div className="container mx-auto text-center px-6">
        <h2 className="text-4xl font-bold mb-12">Our Services</h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-10">
          {/* Web Design */}
          <div className="service-card bg-white p-8 rounded-lg shadow-lg transform transition duration-300 hover:scale-105 hover:shadow-xl">
            <div className="service-icon mb-4">
              <svg xmlns="http://www.w3.org/2000/svg" className="w-16 h-16 mx-auto text-blue-600" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <rect width="20" height="20" rx="5" />
              </svg>
            </div>
            <h3 className="text-xl font-semibold mb-4">Web Design</h3>
            <p>
              Your website is the face of your business. Let us design a visually compelling and highly functional website that makes a lasting impression.
            </p>
          </div>
          {/* Software Development */}
          <div className="service-card bg-white p-8 rounded-lg shadow-lg transform transition duration-300 hover:scale-105 hover:shadow-xl">
            <div className="service-icon mb-4">
              <svg xmlns="http://www.w3.org/2000/svg" className="w-16 h-16 mx-auto text-blue-600" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <circle cx="12" cy="12" r="10" />
              </svg>
            </div>
            <h3 className="text-xl font-semibold mb-4">Software Development</h3>
            <p>
              We specialize in custom software development that meets your unique business needs, helping you scale and optimize your operations.
            </p>
          </div>
          {/* Project Management */}
          <div className="service-card bg-white p-8 rounded-lg shadow-lg transform transition duration-300 hover:scale-105 hover:shadow-xl">
            <div className="service-icon mb-4">
              <svg xmlns="http://www.w3.org/2000/svg" className="w-16 h-16 mx-auto text-blue-600" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <path d="M12 16l4-4-4-4" />
              </svg>
            </div>
            <h3 className="text-xl font-semibold mb-4">Project Management</h3>
            <p>
              Let us handle your projects with expert management, ensuring deadlines are met and goals are achieved efficiently.
            </p>
          </div>
          {/* Cloud Solutions */}
          <div className="service-card bg-white p-8 rounded-lg shadow-lg transform transition duration-300 hover:scale-105 hover:shadow-xl">
            <div className="service-icon mb-4">
              <svg xmlns="http://www.w3.org/2000/svg" className="w-16 h-16 mx-auto text-blue-600" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <path d="M3 12h18M12 3v18" />
              </svg>
            </div>
            <h3 className="text-xl font-semibold mb-4">Cloud Solutions</h3>
            <p>
              Optimize your business with scalable and secure cloud solutions. We provide Azure cloud management, migrations, and cost optimization services.
            </p>
          </div>
          {/* Cybersecurity */}
          <div className="service-card bg-white p-8 rounded-lg shadow-lg transform transition duration-300 hover:scale-105 hover:shadow-xl">
            <div className="service-icon mb-4">
              <svg xmlns="http://www.w3.org/2000/svg" className="w-16 h-16 mx-auto text-blue-600" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <path d="M5 12l5 5L20 7" />
              </svg>
            </div>
            <h3 className="text-xl font-semibold mb-4">Cybersecurity</h3>
            <p>
              Protect your business with top-tier cybersecurity solutions. We provide security audits, penetration testing, and compliance services.
            </p>
          </div>
          {/* IT Consulting */}
          <div className="service-card bg-white p-8 rounded-lg shadow-lg transform transition duration-300 hover:scale-105 hover:shadow-xl">
            <div className="service-icon mb-4">
              <svg xmlns="http://www.w3.org/2000/svg" className="w-16 h-16 mx-auto text-blue-600" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <path d="M9 12l2 2 4-4" />
              </svg>
            </div>
            <h3 className="text-xl font-semibold mb-4">IT Consulting</h3>
            <p>
              Our experts provide strategic IT consulting to align technology with your business objectives, ensuring efficiency and innovation.
            </p>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Services;
