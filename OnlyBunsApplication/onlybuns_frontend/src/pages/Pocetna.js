import React from 'react';
import { AppBar, Toolbar, Typography, Button, Box, Grid, Paper, IconButton } from '@mui/material';
import { Link } from 'react-router-dom';
import logo from './photos/onlybuns_logo.png';
import rabbit1 from './photos/onlybuns_logo.png';
import rabbit2 from './photos/rabbit_hop.jpeg';
import rabbit3 from './photos/rabbit_carrot.jpg';
import rabbit4 from './photos/rabbit_sun.jpeg';
import FavoriteIcon from '@mui/icons-material/Favorite';

export default function HomePage() {
  // Array of rabbit posts with descriptions and like counts
  const rabbitPosts = [
    { img: rabbit1, description: 'Enjoying a sunny day!🌞', likes: 124 },
    { img: rabbit2, description: 'Hopping through the garden 🐇', likes: 98 },
    { img: rabbit3, description: 'Relaxing with a carrot🥕', likes: 85 },
    { img: rabbit4, description: 'Taking a nap in the sun🌞', likes: 67 }
  ];

  return (
    <div>
      {/* Navigation Bar */}
      <AppBar position="static" sx={{ bgcolor: '#b4a7d6' }}>
        <Toolbar sx={{ justifyContent: 'space-between' }}>
          <Box sx={{ display: 'flex', alignItems: 'center', ml: 2 }}>
            <img src={logo} alt="OnlyBuns Logo" style={{ height: '40px', marginRight: '10px' }} />
            <Typography variant="h5" component="div" sx={{ fontWeight: 'bold' }}>
              OnlyBuns
            </Typography>
          </Box>
          <Box sx={{ display: 'flex', gap: 2, mr: 2 }}>
            <Button component={Link} to="/sign-in" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              Sign In
            </Button>
            <Button component={Link} to="/log-in" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              Log In
            </Button>
          </Box>
        </Toolbar>
      </AppBar>

      {/* Main Content */}
      <Box sx={{ padding: 3, textAlign: 'center' }}>
        <Typography variant="h4" sx={{ mt: 5, mb: 2 }}>
          Welcome to OnlyBuns!
        </Typography>
        <Typography variant="body1" sx={{ mb: 4 }}>
          Share and enjoy the cutest photos of rabbits with the OnlyBuns community.
        </Typography>
        <Button variant="contained" color="secondary" sx={{ padding: '10px 20px', borderRadius: '20px', fontSize: '1.2rem', fontWeight: 'bold' }}>
          Start Sharing
        </Button>

        {/* Rabbit Post Cards */}
        <Grid container spacing={3} sx={{ mt: 4 }}>
          {rabbitPosts.map((post, index) => (
            <Grid item xs={12} sm={6} md={3} key={index}>
              <Paper elevation={6} sx={{ padding: 3, borderRadius: '15px', textAlign: 'center', boxShadow: '0 12px 24px rgba(0, 0, 0, 0.1)' }}>
                <img src={post.img} alt={post.title} style={{height: '240px', width: '100%', borderRadius: '10px', marginBottom: '15px' }} />
                
                {/* Title, Caption, and Like Count all on one line */}
                <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <IconButton sx={{ color: '#e91e63' }}>
                      <FavoriteIcon />
                  </IconButton>
                  <Typography variant="body2" sx={{ fontWeight: 'bold' }}>
                      {post.likes} 
                    </Typography>
                  <Typography variant="body2" color="textSecondary" sx={{ fontStyle: 'italic', flex: 1, textAlign: 'center' }}>
                    {post.description}
                  </Typography>
                  <Box sx={{ display: 'flex', alignItems: 'center' }}>
   
                  </Box>
                </Box>
              </Paper>
            </Grid>
          ))}
        </Grid>
      </Box>
    </div>
  );
}
