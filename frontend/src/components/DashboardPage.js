import React, { useState } from 'react';

export default function DashboardPage({ onReportIssue, error, successMessage }) {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [category, setCategory] = useState('OTHER');
  const [imageFile, setImageFile] = useState(null); // To store the selected image file
  const [location, setLocation] = useState({ lat: null, lon: null }); // To store coordinates
  const [locationMessage, setLocationMessage] = useState('No location captured.');

  // Function to get the user's current location using the browser's Geolocation API
  const handleGetLocation = () => {
    if (navigator.geolocation) {
      setLocationMessage('Getting location...');
      // This will trigger a permission pop-up in the browser
      navigator.geolocation.getCurrentPosition(
        (position) => {
          // On success, we update our state with the coordinates
          setLocation({
            lat: position.coords.latitude,
            lon: position.coords.longitude,
          });
          setLocationMessage(`Location captured: ${position.coords.latitude.toFixed(4)}, ${position.coords.longitude.toFixed(4)}`);
        },
        () => {
          // Handle errors, like the user denying permission
          setLocationMessage('Unable to retrieve location. Please check browser permissions.');
        }
      );
    } else {
      setLocationMessage('Geolocation is not supported by this browser.');
    }
  };

  // Function to handle the form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    // We now check if the location has been captured before submitting
    if (!location.lat || !location.lon) {
        alert("Please capture your location before submitting.");
        return;
    }
    
    // In a real app, you would upload the imageFile to a cloud service here
    // and get a URL back. For now, we'll just log it and send a placeholder URL.
    console.log("Simulating image upload for:", imageFile?.name);
    const imageUrl = imageFile ? `https://example.com/images/${imageFile.name}` : null;

    const issueData = {
      title,
      description,
      latitude: location.lat, // Use the real latitude from state
      longitude: location.lon, // Use the real longitude from state
      category,
      imageUrl,
    };

    onReportIssue(issueData);
    
    // Reset form after submission
    setTitle('');
    setDescription('');
    setCategory('OTHER');
    setImageFile(null);
    setLocation({ lat: null, lon: null });
    setLocationMessage('No location captured.');
  };

  return (
    <div className="w-full max-w-2xl">
      <form onSubmit={handleSubmit} className="bg-white shadow-lg rounded-xl px-8 pt-6 pb-8 mb-4">
        <h2 className="text-3xl font-bold text-center text-gray-800 mb-6">Report a New Issue</h2>
        {error && <p className="bg-red-100 text-red-700 p-3 rounded-lg mb-4 text-center">{error}</p>}
        {successMessage && <p className="bg-green-100 text-green-700 p-3 rounded-lg mb-4 text-center">{successMessage}</p>}
        
        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="title">Title</label>
          <input
            className="shadow-sm appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500"
            id="title" type="text" placeholder="e.g., Large Pothole on Main St"
            value={title} onChange={(e) => setTitle(e.target.value)} required
          />
        </div>

        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="description">Description</label>
          <textarea
            className="shadow-sm appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500"
            id="description" rows="4" placeholder="Provide details about the issue..."
            value={description} onChange={(e) => setDescription(e.target.value)}
          ></textarea>
        </div>
        
        {/* New section for Category and Image Upload */}
        <div className="mb-4 grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
                <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="category">Category</label>
                <select id="category" value={category} onChange={(e) => setCategory(e.target.value)}
                    className="shadow-sm appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <option value="ROAD_WORKS">Road Works</option>
                    <option value="WATER_SUPPLY">Water Supply</option>
                    <option value="SANITATION">Sanitation</option>
                    <option value="STREET_LIGHTS">Street Lights</option>
                    <option value="PARKS_AND_RECREATION">Parks and Recreation</option>
                    <option value="OTHER">Other</option>
                </select>
            </div>
            <div>
                <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="image">Upload Image (Optional)</label>
                <input
                    className="block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 focus:outline-none"
                    id="image" type="file" accept="image/*"
                    onChange={(e) => setImageFile(e.target.files[0])}
                />
            </div>
        </div>

        {/* New section for Location */}
        <div className="mb-6">
            <label className="block text-gray-700 text-sm font-bold mb-2">Location</label>
            <div className="flex items-center space-x-4">
                <button type="button" onClick={handleGetLocation} className="bg-gray-200 hover:bg-gray-300 text-gray-800 font-bold py-2 px-4 rounded-lg">
                    Get Current Location
                </button>
                <p className="text-sm text-gray-600">{locationMessage}</p>
            </div>
        </div>

        <div className="flex items-center justify-center">
          <button className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-6 rounded-lg focus:outline-none focus:shadow-outline w-full" type="submit">
            Submit Report
          </button>
        </div>
      </form>
    </div>
  );
}