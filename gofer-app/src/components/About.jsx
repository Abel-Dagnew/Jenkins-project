/* eslint-disable no-unused-vars */
import React from 'react';
import { FaLinkedin } from 'react-icons/fa';

const teamMembers = [
  {
    name: 'Abel Dagnew',
    role: 'Cloud Engineer',
    image: 'abel.jpg',
    description: 'Experienced entrepreneur with over 15 years in the tech industry, specializing in leadership and strategy.',
    linkedin: 'https://www.linkedin.com/in/abel-dagnew-948351224/',
  },
  {
    name: 'Bruhtesfa Enyew',
    role: 'Lead Developer',
    image: 'bruh.jpg',
    description: 'Skilled developer with expertise in full-stack web development, focusing on scalable applications and clean code.',
    linkedin: 'https://www.linkedin.com/in/bruhtesfa-enyew/',
  },
  {
    name: 'Amanuel Genetu',
    role: 'DevOps Enineer',
    image: 'aman.jpg',
    description: 'Creative designer with a passion for creating intuitive and beautiful user interfaces that enhance user experiences.',
    linkedin: 'https://www.linkedin.com/in/amanuel-genetu-4b3761213/',
  },
  {
    name: 'Yosef Nakachew',
    role: 'UI/UX Designer',
    image: 'joc.jpg',
    description: 'SEO expert with a proven track record of increasing organic traffic through strategic on-page and off-page optimization.',
    linkedin: 'https://www.linkedin.com/in/yosefdegarege/',
  },
];

const About = () => {
  return (
    <section className="about bg-gray-50 min-h-screen flex flex-col lg:flex-row justify-center items-center p-6 md:p-12 lg:p-16">
      {/* About Us Section */}
      <div className="lg:w-1/2 text-left px-8 space-y-6">
        <h2 className="text-5xl font-extrabold text-gray-800">About Us</h2>
        <p className="mt-4 text-xl text-gray-700 leading-relaxed">
          Gofer Technology Plc, founded in 2012, is a professional IT company that offers web design, SEO, software development, and IT services. We aim to push the limits of creativity and deliver high-quality work with fast turnaround times.
        </p>
      </div>

      {/* Meet Our Team Section */}
      <div className="lg:w-1/2 text-center px-8 mt-12 lg:mt-0">
        <h3 className="text-4xl font-bold text-gray-800 mb-8">Meet Our Team</h3>
        <div className="mt-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-2 gap-8">
          {teamMembers.map((member, index) => (
            <div
              key={index}
              className="relative group w-full sm:w-64 md:w-72 lg:w-80 xl:w-96 h-auto rounded-lg overflow-hidden shadow-lg bg-white transition-all duration-300 flex flex-col items-center p-6"
            >
              {/* Image and Name Section */}
              <img
                src={member.image}
                alt={member.name}
                className="w-32 h-32 object-cover rounded-full border-4 border-gray-300 shadow-md transition-transform duration-300"
              />
              <h4 className="mt-4 text-2xl font-semibold text-gray-800">{member.name}</h4>
              <p className="text-lg text-gray-600">{member.role}</p>

              {/* Hover Effect for Description */}
              <div className="absolute inset-0 bg-black bg-opacity-90 flex flex-col justify-center items-center text-white opacity-0 group-hover:opacity-100 transition-opacity duration-300 p-6 w-full h-full">
              <img
                src={member.image}
                alt={member.name}
                className="w-15 h-15 object-cover rounded-full border-4 border-gray-300 shadow-md transition-transform duration-300"
              />
                <h4 className="text-lg font-semibold">{member.name}</h4>
                <p className="text-sm font-light text-gray-300">{member.role}</p>
                <p className="mt-4 text-md text-gray-200 text-center max-w-xs">{member.description}</p>
                {/* LinkedIn Icon on Hover */}
                <a
                  href={member.linkedin}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="mt-4 text-blue-500 hover:text-blue-700 text-3xl"
                >
                  <FaLinkedin />
                </a>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

export default About;
