import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Typography, Paper, Box } from '@mui/material';

const ObjavaPrikaz = () => {
  const { postId } = useParams();
  const [post, setPost] = useState(null);

  useEffect(() => {
    // Fetch post details using postId
    fetch(`http://localhost:8080/objava/${postId}`)
      .then(response => response.json())
      .then(data => setPost(data))
      .catch(error => console.error('Error fetching post:', error));
  }, [postId]);

  if (!post) {
    return <Typography>Loading...</Typography>;
  }

  return (
    <Paper elevation={6} sx={{ padding: 3, borderRadius: '15px', width:'400px', marginTop: 4 }}>
      <img
        src={`http://localhost:8080/images/${post.slika}`}
        alt={post.opis}
        style={{ height: '300px', width: '100%', borderRadius: '10px', marginBottom: '15px' }}
      />
      <Box sx={{ mb: 2 }}>
        <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
          {post.korisnicko_ime}
        </Typography>
        <Typography variant="body2" color="textSecondary" sx={{ fontStyle: 'italic', mb: 1 }}>
          {post.opis}
        </Typography>
        <Typography variant="body2">
          Likes: {post.broj_lajkova} | Comments: {post.broj_komentara}
        </Typography>
      </Box>
    </Paper>
  );
};

export default ObjavaPrikaz;
