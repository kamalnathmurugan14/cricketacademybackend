import React, { useState } from 'react';
import { Users, Clock, Star, Calendar, ArrowRight } from 'lucide-react';

const Coaching: React.FC = () => {
  const [selectedCategory, setSelectedCategory] = useState('All');

  const categories = [
    { name: 'All', description: 'All coaching programs', ageRange: '' },
    { name: 'Kids', description: 'Fun cricket for young players', ageRange: '5-12 years' },
    { name: 'Teens', description: 'Competitive training for teenagers', ageRange: '13-17 years' },
    { name: 'Adults', description: 'Professional coaching for adults', ageRange: '18+ years' }
  ];

  const coaches = [
    {
      id: '1',
      name: 'Rahul Sharma',
      specialization: 'Batting Coach',
      experience: '8 years',
      rating: 4.9,
      photo: 'https://images.pexels.com/photos/3621104/pexels-photo-3621104.jpeg?auto=compress&cs=tinysrgb&w=200',
      qualifications: ['Level 3 Certified', 'Former State Player'],
      categories: ['Kids', 'Teens', 'Adults']
    },
    {
      id: '2',
      name: 'Priya Patel',
      specialization: 'Bowling Coach',
      experience: '6 years',
      rating: 4.8,
      photo: 'https://images.pexels.com/photos/3621227/pexels-photo-3621227.jpeg?auto=compress&cs=tinysrgb&w=200',
      qualifications: ['Level 2 Certified', 'National Player'],
      categories: ['Teens', 'Adults']
    },
    {
      id: '3',
      name: 'Arjun Singh',
      specialization: 'All-Rounder Coach',
      experience: '10 years',
      rating: 4.9,
      photo: 'https://images.pexels.com/photos/3621227/pexels-photo-3621227.jpeg?auto=compress&cs=tinysrgb&w=200',
      qualifications: ['Level 3 Certified', 'Former Professional'],
      categories: ['Kids', 'Teens', 'Adults']
    }
  ];

  const programs = [
    {
      id: '1',
      title: 'Kids Cricket Foundation',
      category: 'Kids',
      duration: '1 hour',
      maxStudents: 8,
      currentStudents: 5,
      price: '₹1,999/month',
      description: 'Fun-based cricket learning with basic skills development',
      schedule: ['Mon 4:00 PM', 'Wed 4:00 PM', 'Fri 4:00 PM'],
      coach: 'Rahul Sharma'
    },
    {
      id: '2',
      title: 'Teen Competitive Training',
      category: 'Teens',
      duration: '1.5 hours',
      maxStudents: 10,
      currentStudents: 8,
      price: '₹2,999/month',
      description: 'Advanced techniques and competitive match preparation',
      schedule: ['Tue 5:00 PM', 'Thu 5:00 PM', 'Sat 9:00 AM'],
      coach: 'Priya Patel'
    },
    {
      id: '3',
      title: 'Adult Professional Training',
      category: 'Adults',
      duration: '2 hours',
      maxStudents: 12,
      currentStudents: 10,
      price: '₹4,999/month',
      description: 'Professional level coaching for serious players',
      schedule: ['Mon 6:00 PM', 'Wed 6:00 PM', 'Sat 7:00 AM'],
      coach: 'Arjun Singh'
    },
    {
      id: '4',
      title: 'Individual Coaching',
      category: 'All',
      duration: '1 hour',
      maxStudents: 1,
      currentStudents: 0,
      price: '₹2,000/session',
      description: 'One-on-one personalized coaching sessions',
      schedule: ['Flexible timing'],
      coach: 'Any Coach'
    }
  ];

  const filteredPrograms = selectedCategory === 'All' 
    ? programs 
    : programs.filter(program => program.category === selectedCategory || program.category === 'All');

  const filteredCoaches = selectedCategory === 'All'
    ? coaches
    : coaches.filter(coach => coach.categories.includes(selectedCategory));

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="text-center mb-12">
          <h1 className="text-4xl font-bold text-gray-900 mb-4">Coaching Programs</h1>
          <p className="text-xl text-gray-600 max-w-3xl mx-auto">
            Professional cricket coaching for all ages and skill levels. Learn from experienced coaches 
            and take your game to the next level.
          </p>
        </div>

        {/* Category Selection */}
        <div className="mb-8">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            {categories.map((category) => (
              <button
                key={category.name}
                onClick={() => setSelectedCategory(category.name)}
                className={`p-6 rounded-lg border-2 transition-all duration-300 ${
                  selectedCategory === category.name
                    ? 'border-green-600 bg-green-50 text-green-900'
                    : 'border-gray-200 bg-white hover:border-green-300 hover:bg-green-50'
                }`}
              >
                <h3 className="font-bold text-lg mb-2">{category.name}</h3>
                <p className="text-sm text-gray-600 mb-1">{category.description}</p>
                {category.ageRange && (
                  <p className="text-xs font-medium text-green-600">{category.ageRange}</p>
                )}
              </button>
            ))}
          </div>
        </div>

        {/* Coaches Section */}
        <div className="mb-12">
          <h2 className="text-2xl font-bold text-gray-900 mb-6">Our Expert Coaches</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {filteredCoaches.map((coach) => (
              <div key={coach.id} className="bg-white rounded-lg shadow-lg overflow-hidden hover:shadow-xl transition-shadow">
                <div className="p-6">
                  <div className="flex items-center mb-4">
                    <img
                      src={coach.photo}
                      alt={coach.name}
                      className="w-16 h-16 rounded-full object-cover mr-4"
                    />
                    <div>
                      <h3 className="text-lg font-bold text-gray-900">{coach.name}</h3>
                      <p className="text-green-600 font-medium">{coach.specialization}</p>
                    </div>
                  </div>
                  
                  <div className="space-y-2 mb-4">
                    <div className="flex items-center justify-between">
                      <span className="text-sm text-gray-600">Experience:</span>
                      <span className="text-sm font-medium">{coach.experience}</span>
                    </div>
                    <div className="flex items-center justify-between">
                      <span className="text-sm text-gray-600">Rating:</span>
                      <div className="flex items-center">
                        <Star className="h-4 w-4 text-yellow-400 fill-current" />
                        <span className="text-sm font-medium ml-1">{coach.rating}</span>
                      </div>
                    </div>
                  </div>
                  
                  <div className="mb-4">
                    <h4 className="text-sm font-medium text-gray-700 mb-2">Qualifications:</h4>
                    <div className="flex flex-wrap gap-2">
                      {coach.qualifications.map((qual, index) => (
                        <span
                          key={index}
                          className="px-2 py-1 bg-green-100 text-green-800 text-xs rounded-full"
                        >
                          {qual}
                        </span>
                      ))}
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Programs Section */}
        <div>
          <h2 className="text-2xl font-bold text-gray-900 mb-6">Available Programs</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {filteredPrograms.map((program) => (
              <div key={program.id} className="bg-white rounded-lg shadow-lg overflow-hidden hover:shadow-xl transition-shadow">
                <div className="p-6">
                  <div className="flex items-center justify-between mb-4">
                    <h3 className="text-xl font-bold text-gray-900">{program.title}</h3>
                    <span className="px-3 py-1 bg-green-100 text-green-800 text-sm font-medium rounded-full">
                      {program.category}
                    </span>
                  </div>
                  
                  <p className="text-gray-600 mb-4">{program.description}</p>
                  
                  <div className="grid grid-cols-2 gap-4 mb-4">
                    <div className="flex items-center">
                      <Clock className="h-4 w-4 text-gray-400 mr-2" />
                      <span className="text-sm text-gray-600">{program.duration}</span>
                    </div>
                    <div className="flex items-center">
                      <Users className="h-4 w-4 text-gray-400 mr-2" />
                      <span className="text-sm text-gray-600">
                        {program.currentStudents}/{program.maxStudents} students
                      </span>
                    </div>
                  </div>
                  
                  <div className="mb-4">
                    <h4 className="text-sm font-medium text-gray-700 mb-2">Schedule:</h4>
                    <div className="flex flex-wrap gap-2">
                      {program.schedule.map((time, index) => (
                        <span
                          key={index}
                          className="px-2 py-1 bg-blue-100 text-blue-800 text-xs rounded"
                        >
                          {time}
                        </span>
                      ))}
                    </div>
                  </div>
                  
                  <div className="flex items-center justify-between">
                    <div>
                      <span className="text-2xl font-bold text-green-600">{program.price}</span>
                      <p className="text-sm text-gray-500">Coach: {program.coach}</p>
                    </div>
                    <button className="inline-flex items-center bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700 transition-colors">
                      Enroll Now
                      <ArrowRight className="ml-2 h-4 w-4" />
                    </button>
                  </div>
                  
                  {program.currentStudents >= program.maxStudents && (
                    <div className="mt-3 p-2 bg-yellow-50 border border-yellow-200 rounded-md">
                      <p className="text-yellow-800 text-sm font-medium">
                        Program is currently full. Join waitlist?
                      </p>
                    </div>
                  )}
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Coaching;