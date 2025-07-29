import React from 'react';
import { Link } from 'react-router-dom';
import { ArrowRight, Users, MapPin, Phone, Mail, Star } from 'lucide-react';
import HeroSection from '../components/HeroSection';
import CricketerCard from '../components/CricketerCard';
import TestimonialCard from '../components/TestimonialCard';
import FacilityCard from '../components/FacilityCard';

const Homepage: React.FC = () => {
  const cricketers = [
    {
      id: '1',
      name: 'Rahul Sharma',
      photo: 'https://images.pexels.com/photos/3621104/pexels-photo-3621104.jpeg?auto=compress&cs=tinysrgb&w=300',
      achievements: ['State Player', 'Best Batsman 2023', '500+ Matches'],
      stats: {
        2021: { runs: 1200, wickets: 5, matches: 25 },
        2022: { runs: 1450, wickets: 8, matches: 28 },
        2023: { runs: 1650, wickets: 12, matches: 32 }
      }
    },
    {
      id: '2',
      name: 'Priya Patel',
      photo: 'https://images.pexels.com/photos/3621227/pexels-photo-3621227.jpeg?auto=compress&cs=tinysrgb&w=300',
      achievements: ['National Player', 'Best Bowler 2022', '300+ Wickets'],
      stats: {
        2021: { runs: 800, wickets: 45, matches: 22 },
        2022: { runs: 950, wickets: 52, matches: 25 },
        2023: { runs: 1100, wickets: 48, matches: 28 }
      }
    },
    {
      id: '3',
      name: 'Arjun Singh',
      photo: 'https://images.pexels.com/photos/3621227/pexels-photo-3621227.jpeg?auto=compress&cs=tinysrgb&w=300',
      achievements: ['District Captain', 'All-rounder 2023', '200+ Matches'],
      stats: {
        2021: { runs: 1000, wickets: 25, matches: 20 },
        2022: { runs: 1300, wickets: 30, matches: 24 },
        2023: { runs: 1500, wickets: 35, matches: 26 }
      }
    }
  ];

  const testimonials = [
    {
      name: 'Amit Kumar',
      rating: 5,
      comment: 'Excellent coaching and facilities. My son has improved tremendously!',
      photo: 'https://images.pexels.com/photos/1040880/pexels-photo-1040880.jpeg?auto=compress&cs=tinysrgb&w=150'
    },
    {
      name: 'Sarah Johnson',
      rating: 5,
      comment: 'Professional coaches and well-maintained grounds. Highly recommended!',
      photo: 'https://images.pexels.com/photos/1130626/pexels-photo-1130626.jpeg?auto=compress&cs=tinysrgb&w=150'
    },
    {
      name: 'Raj Patel',
      rating: 5,
      comment: 'Great academy with modern facilities and experienced coaches.',
      photo: 'https://images.pexels.com/photos/1043471/pexels-photo-1043471.jpeg?auto=compress&cs=tinysrgb&w=150'
    }
  ];

  const facilities = [
    {
      title: 'Professional Grounds',
      description: 'Multiple well-maintained cricket grounds with modern facilities',
      image: 'https://images.pexels.com/photos/3621227/pexels-photo-3621227.jpeg?auto=compress&cs=tinysrgb&w=400',
      features: ['3 Match Grounds', 'Professional Pitch', 'Floodlights']
    },
    {
      title: 'Practice Nets',
      description: 'State-of-the-art practice nets for skill development',
      image: 'https://images.pexels.com/photos/3621104/pexels-photo-3621104.jpeg?auto=compress&cs=tinysrgb&w=400',
      features: ['10 Practice Nets', 'Bowling Machines', 'Video Analysis']
    },
    {
      title: 'Modern Equipment',
      description: 'Latest cricket equipment and training aids available',
      image: 'https://images.pexels.com/photos/3621227/pexels-photo-3621227.jpeg?auto=compress&cs=tinysrgb&w=400',
      features: ['Premium Bats', 'Safety Gear', 'Training Equipment']
    }
  ];

  return (
    <div className="min-h-screen">
      <HeroSection />
      
      {/* Featured Cricketers Section */}
      <section className="py-16 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl font-bold text-gray-900 mb-4">Our Star Players</h2>
            <p className="text-lg text-gray-600 max-w-2xl mx-auto">
              Learn from the best! Our academy features talented cricketers who bring professional experience to your training.
            </p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            {cricketers.map((cricketer) => (
              <CricketerCard key={cricketer.id} cricketer={cricketer} />
            ))}
          </div>
        </div>
      </section>

      {/* Facilities Section */}
      <section className="py-16 bg-gray-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl font-bold text-gray-900 mb-4">World-Class Facilities</h2>
            <p className="text-lg text-gray-600 max-w-2xl mx-auto">
              Train with professional-grade equipment and facilities designed to elevate your game.
            </p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            {facilities.map((facility, index) => (
              <FacilityCard key={index} facility={facility} />
            ))}
          </div>
        </div>
      </section>

      {/* Testimonials Section */}
      <section className="py-16 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl font-bold text-gray-900 mb-4">What Our Members Say</h2>
            <p className="text-lg text-gray-600">
              Hear from our satisfied players and parents about their experience.
            </p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            {testimonials.map((testimonial, index) => (
              <TestimonialCard key={index} testimonial={testimonial} />
            ))}
          </div>
        </div>
      </section>

      {/* Contact Section */}
      <section className="py-16 bg-green-600 text-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12">
            <div>
              <h2 className="text-3xl font-bold mb-8">Get in Touch</h2>
              <div className="space-y-6">
                <div className="flex items-center space-x-4">
                  <MapPin className="h-6 w-6" />
                  <div>
                    <p className="font-semibold">Address</p>
                    <p>123 Cricket Ground Road, Sports City, SC 12345</p>
                  </div>
                </div>
                <div className="flex items-center space-x-4">
                  <Phone className="h-6 w-6" />
                  <div>
                    <p className="font-semibold">Phone</p>
                    <p>+1 (555) 123-4567</p>
                  </div>
                </div>
                <div className="flex items-center space-x-4">
                  <Mail className="h-6 w-6" />
                  <div>
                    <p className="font-semibold">Email</p>
                    <p>info@cricketacademy.com</p>
                  </div>
                </div>
              </div>
            </div>
            
            <div className="bg-white/10 backdrop-blur-lg rounded-lg p-6">
              <h3 className="text-xl font-bold mb-4">Ready to Start Your Cricket Journey?</h3>
              <p className="mb-6">Join our academy and take your cricket skills to the next level with professional coaching and world-class facilities.</p>
              <Link
                to="/register"
                className="inline-flex items-center bg-white text-green-600 px-6 py-3 rounded-md font-semibold hover:bg-gray-100 transition-colors"
              >
                Register Now
                <ArrowRight className="ml-2 h-4 w-4" />
              </Link>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Homepage;