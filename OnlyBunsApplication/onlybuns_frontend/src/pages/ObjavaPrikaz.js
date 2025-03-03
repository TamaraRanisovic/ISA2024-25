import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Typography, Paper, Box } from '@mui/material';
import { AppBar, Toolbar, Button, Grid, IconButton } from '@mui/material';
import { Link } from 'react-router-dom';
import logo from './photos/onlybuns_logo.png';
import ChatBubbleOutlineIcon from "@mui/icons-material/ChatBubbleOutline";
import FavoriteIcon from "@mui/icons-material/Favorite";

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
    <div>
    {/* Navigation Bar */}
    <AppBar position="static" sx={{ bgcolor: '#b4a7d6' }}>
    <Toolbar sx={{ justifyContent: 'space-between' }}>
      <Link to="/" style={{ textDecoration: 'none', display: 'flex', alignItems: 'center', color: 'inherit' }}>
      <Box sx={{ display: 'flex', alignItems: 'center', ml: 2 }}>
        <img src={logo} alt="OnlyBuns Logo" style={{ height: '40px', marginRight: '10px' }} />
        <Typography variant="h5" component="div" sx={{ fontWeight: 'bold' }}>
          OnlyBuns
        </Typography>
      </Box>
      </Link>
      <Box sx={{ display: 'flex', gap: 2, mr: 2 }}>
        <Button component={Link} to="/registracija" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
          Sign In
        </Button>
        <Button component={Link} to="/prijava" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
          Log In
        </Button>
      </Box>
    </Toolbar>
  </AppBar>
  <Paper
      elevation={6}
      sx={{
        padding: 3,
        borderRadius: "15px",
        textAlign: "center",
        boxShadow: "0 12px 24px rgba(0, 0, 0, 0.1)",
        width: "380px",
        margin: "auto",
        mt:3,
      }}
    >
      <img
        src={`http://localhost:8080/images/${post.slika}`}
        alt={post.opis}
        style={{
          height: "310px",
          width: "100%",
          borderRadius: "10px",
          marginBottom: "15px",
        }}
      />

      {/* Likes and Comments Row */}
      <Box sx={{ display: "flex", justifyContent: "center", gap: 1, alignItems: "center", mb: 1 }}>
        <Box sx={{ display: "flex", alignItems: "center" }}>
          <IconButton sx={{ color: "#e91e63" }}>
            <FavoriteIcon />
          </IconButton>
          <Typography variant="body2" sx={{ fontWeight: "bold" }}>
            {post.broj_lajkova}
          </Typography>
        </Box>
        <Box sx={{ display: "flex", alignItems: "center" }}>
          <IconButton color="secondary">
            <ChatBubbleOutlineIcon />
          </IconButton>
          <Typography variant="body2" sx={{ fontWeight: "bold" }}>
            {post.broj_komentara}
          </Typography>
        </Box>
      </Box>

      {/* Description Row */}
      <Box sx={{ display: "flex", flexDirection: "column", alignItems: "flex-start" }}>
        <Link to={`/profilKorisnika/${post.korisnicko_ime}`} style={{ textDecoration: "none" }}>
          <Typography sx={{ fontWeight: "bold" }}>{post.korisnicko_ime}</Typography>
        </Link>
        <Typography
          variant="body2"
          color="textSecondary"
          sx={{ fontStyle: "italic", whiteSpace: "normal", wordWrap: "break-word" }}
        >
          {post.opis}
        </Typography>
      </Box>
    </Paper>
    </div>
  );
};

export default ObjavaPrikaz;
