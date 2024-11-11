import React, { useEffect, useState } from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { Link } from 'react-router-dom';
import logo from './photos/onlybuns_logo.png';
import rabbit1 from './photos/onlybuns_logo.png';
import rabbit2 from './photos/rabbit_hop.jpeg';
import rabbit3 from 'C:\\Users\\Lenovo\\Documents\\GitHub\\ISA2024-25\\OnlyBunsApplication\\onlybuns_frontend\\src\\pages\\photos\\rabbit_carrot.jpg';
import rabbit4 from './photos/rabbit_sun.jpeg';
import FavoriteIcon from '@mui/icons-material/Favorite';
import {Grid, Paper, IconButton } from '@mui/material';
import ChatBubbleOutlineIcon from '@mui/icons-material/ChatBubbleOutline';

const PrijavljeniKorisnikPregled = () => {
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [role, setRole] = useState('');
  const token = localStorage.getItem('jwtToken'); // Get JWT token from localStorage
  const [rabbitPosts, setRabbitPosts] = useState([]); // State to store posts from the database

  /*const rabbitPosts = [
    { img: rabbit1, user: "bunny123", description: 'Enjoying a sunny day!🌞', likes: 124, comments: 27 },
    { img: rabbit2, user: "rabbit10", description: 'Hopping through the garden 🐇', likes: 98, comments: 15 },
    { img: rabbit3, user: "CarrotBunny", description: 'Relaxing with a carrot🥕', likes: 85,  comments: 9 },
    { img: rabbit4, user: "CuteRabbit", description: 'Taking a nap in the sun🌞', likes: 67,  comments: 17 }
  ];*/

  // Function to decode the JWT token by calling the backend endpoint
  useEffect(() => {
    if (token) {
      // Send the token in the body of the POST request (not in the Authorization header)
      fetch('http://localhost:8080/auth/decodeJwt', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json', // Indicate that we're sending JSON data
        },
        body: token // Send the token in the request body
      })
        .then(response => response.json())
        .then(data => {
          // Assuming the response contains 'email' and 'role' from the decoded token
          if (data) {
            setEmail(data.Email); // Set email from the response
            setName(data.Username); // Set email from the response
            setRole(data.Role);   // Set role from the response
          }
        })
        .catch(error => {
          console.error('Error decoding JWT token:', error);
        });
    }
  }, [token]);

  useEffect(() => {
    fetch('http://localhost:8080/objava', {
      headers: {
        //'Authorization': `Bearer ${token}`, // Include JWT token if needed for authentication
        'Content-Type': 'application/json',
      }
    })
      .then(response => response.json())
      .then(data => {
        setRabbitPosts(data); // Store the fetched posts in state
      })
      .catch(error => {
        console.error('Error fetching posts:', error);
      });
  }, [token]);

  return (
    <div>
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
          <Box sx={{ display: 'flex', gap: 2 }}>
            <Button component={Link} to="/prijavljeniKorisnikPregled" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              Feed
            </Button>
            <Button component={Link} to="/shop" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              Trends
            </Button>
            <Button component={Link} to="/about" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              Nearby Posts
            </Button>
            <Button component={Link} to="/contact" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              Chat
            </Button>
            <Button component={Link} to="/contact" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              My profile
            </Button>
          </Box>
          <Box sx={{ display: 'flex', gap: 2, mr: 2, alignItems: 'center' }}>
            {token && name ? ( 
              <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
                Welcome, {name}
              </Typography>
            ) : (
              <>
                <Button component={Link} to="/registracija" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
                  Sign In
                </Button>
                <Button component={Link} to="/prijava" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
                  Log In
                </Button>
              </>
            )}
          </Box>
        </Toolbar>
      </AppBar>
      {/* Rabbit Post Cards */}
      <Grid container spacing={3} sx={{ mt: 4 }}>
        {rabbitPosts.map((post, index) => (
          <Grid item xs={12} sm={6} md={3} key={index}>
            <Paper elevation={6} sx={{ padding: 3, borderRadius: '15px', textAlign: 'center', boxShadow: '0 12px 24px rgba(0, 0, 0, 0.1)' }}>
            <img src={`http://localhost:8080/images/${post.slika}`} alt={post.opis} style={{height: '240px', width: '100%', borderRadius: '10px', marginBottom: '15px' }} />
              
              {/* Likes and Comments Row */}
              <Box sx={{ display: 'flex', justifyContent: 'center', gap: 1, alignItems: 'center', mb: 1 }}>
                <Box sx={{ display: 'flex', alignItems: 'center' }}>
                  
                  <IconButton sx={{ color: '#e91e63' }}>
                    <FavoriteIcon />
                  </IconButton>
                  <Typography variant="body2" sx={{ fontWeight: 'bold' }}>
                    {post.broj_lajkova}
                  </Typography>
                </Box>
                <Box sx={{ display: 'flex', alignItems: 'center' }}>
                  <IconButton color="secondary">
                    <ChatBubbleOutlineIcon />
                  </IconButton>
                  <Typography variant="body2" sx={{ fontWeight: 'bold' }}>
                    {post.broj_komentara}
                  </Typography>
                </Box>
              </Box>

              {/* Description Row */}
              <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
                <Typography sx={{ fontWeight: 'bold' }}>
                  {post.korisnicko_ime}
                </Typography>
                <Typography variant="body2" color="textSecondary" sx={{ fontStyle: 'italic', whiteSpace: 'normal', wordWrap: 'break-word' }}>
                  {post.opis}
                </Typography>
              </Box>

            </Paper>
          </Grid>
        ))}
    </Grid>

    </div>
  );
};

export default PrijavljeniKorisnikPregled;
