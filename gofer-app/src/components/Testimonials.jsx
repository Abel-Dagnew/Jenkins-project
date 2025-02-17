/* eslint-disable no-unused-vars */
import React, { useState, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";

const testimonials = [
  {
    text: "My new site is so much faster and easier to work with than my old site. I just choose the page, make the change and click save. Thanks, guys!",
    name: "Bruk Andualem",
    title: "Red Cross IT Manager",
  },
  {
    text: "I just wanted to share a quick note and let you know that you guys do a really good job. It's really great how easy your websites are to update and manage.",
    name: "Yossef Andualem",
    title: "Twaddel CEO",
  },
  {
    text: "Gofer Technologies transformed our digital presence. Their expertise in cloud solutions and web design is unmatched!",
    name: "Sarah Johnson",
    title: "Tech Startup Founder",
  },
];

const Testimonials = () => {
  const [index, setIndex] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setIndex((prevIndex) => (prevIndex + 1) % testimonials.length);
    }, 5000);
    return () => clearInterval(interval);
  }, []);

  return (
    <section className="w-full h-screen flex items-center justify-center bg-blue-100">
      <div className="max-w-2xl text-center px-6">
        <h2 className="text-4xl font-bold mb-8">What Our Clients Say</h2>
        <div className="relative overflow-hidden h-48">
          <AnimatePresence mode="wait">
            <motion.div
              key={index}
              initial={{ opacity: 0, x: 50 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: -50 }}
              transition={{ duration: 0.8 }}
              className="absolute w-full"
            >
              <p className="text-lg italic">"{testimonials[index].text}"</p>
              <h4 className="mt-4 font-semibold text-xl">{testimonials[index].name}</h4>
              <p className="text-sm text-gray-600">{testimonials[index].title}</p>
            </motion.div>
          </AnimatePresence>
        </div>
      </div>
    </section>
  );
};

export default Testimonials;
