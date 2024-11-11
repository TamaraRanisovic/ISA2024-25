import React, { useState } from 'react';

const NovaObjava = () => {
  const [description, setDescription] = useState('');
  const [photo, setPhoto] = useState(null);
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    // FormData allows you to send files along with text fields in the same request
    const formData = new FormData();
    formData.append('description', description);
    if (photo) {
      formData.append('photo', photo);
    }

    try {
      const response = await fetch('http://localhost:8080/objava/add', {
        method: 'POST',
        body: formData,
      });

      if (response.ok) {
        setSuccessMessage('Post created successfully!');
        setDescription('');
        setPhoto(null);
      } else {
        setErrorMessage('Error creating post.');
      }
    } catch (error) {
      setErrorMessage('Error creating post: ' + error.message);
    }
  };

  // Handle file selection
  const handlePhotoChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setPhoto(file);
    }
  };

  return (
    <div style={{ maxWidth: '500px', margin: 'auto', padding: '20px' }}>
      <h2>Create New Post</h2>

      {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}
      {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}

      <form onSubmit={handleSubmit} encType="multipart/form-data">
        <div style={{ marginBottom: '15px' }}>
          <label>Description:</label>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="What's on your mind?"
            rows="4"
            style={{ width: '100%', padding: '10px' }}
          />
        </div>

        <div style={{ marginBottom: '15px' }}>
          <label>Upload Photo:</label>
          <input type="file" accept="image/*" onChange={handlePhotoChange} />
        </div>

        <button type="submit" style={{ padding: '10px 20px' }}>Create Post</button>
      </form>
    </div>
  );
};

export default NovaObjava;
