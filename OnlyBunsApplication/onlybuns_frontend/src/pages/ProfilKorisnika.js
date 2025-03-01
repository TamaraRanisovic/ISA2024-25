import React from 'react';
import { AppBar, Toolbar, Avatar, Typography, Button, Box, Grid, Paper, IconButton } from '@mui/material';
import { Link } from 'react-router-dom';
import logo from './photos/onlybuns_logo.png';
import FavoriteIcon from '@mui/icons-material/Favorite';
import { useEffect, useState } from 'react';
import ChatBubbleOutlineIcon from '@mui/icons-material/ChatBubbleOutline';
import { useParams } from 'react-router-dom';

export default function ProfilKorisnika() {
  const { username } = useParams();
  const [rabbitPosts, setRabbitPosts] = useState([]); // State to store posts from the database
  const [user, setUser] = useState(null);




  useEffect(() => {
    // Fetch user details
    fetch(`http://localhost:8080/registrovaniKorisnik/username/${username}`) // Replace '1' with dynamic user ID if needed
      .then(response => response.json())
      .then(data => setUser(data))
      .catch(error => console.error('Error fetching user:', error));

    // Fetch user posts
    fetch(`http://localhost:8080/objava/user/${username}`, {
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
  }, []);


  if (!user) return <Typography>Loading profile...</Typography>;


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

      {/* Page Content */}
      <Box sx={{ padding: 4, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        
      <Paper
  elevation={3}
  sx={{
    textAlign: 'center',
    padding: 3, // Reduced padding
    bgcolor: '#fff',
    borderRadius: '16px',
    boxShadow: '0px 4px 12px rgba(0,0,0,0.1)',
    maxWidth: 320, // Reduced maxWidth
    width: '100%',
  }}
>
  <Avatar
  src={logo}
  alt={user.korisnickoIme || 'User'}
    sx={{ width: 80, height: 80, margin: 'auto', border: '3px solid #b4a7d6' }} // Reduced avatar size
  />

  <Typography variant="h6" sx={{ fontWeight: 'bold', mt: 1.5 }}>{user.korisnickoIme || 'Unknown User'}</Typography>



  {/* Follow Stats */}
  <Box sx={{ display: 'flex', justifyContent: 'center', gap: 3, mt: 2 }}>
    <Box sx={{ textAlign: 'center' }}>
      <Typography variant="body1" sx={{ fontWeight: 'bold', color: '#b4a7d6' }}>
        {user.num_followers ?? 0}
      </Typography>
      <Typography variant="body2" sx={{ color: '#555' }}>Followers</Typography>
    </Box>

    <Box sx={{ textAlign: 'center' }}>
      <Typography variant="body1" sx={{ fontWeight: 'bold', color: '#b4a7d6' }}>
        {user.num_following ?? 0}
      </Typography>
      <Typography variant="body2" sx={{ color: '#555' }}>Following</Typography>
    </Box>
  </Box>

  {/* Follow Button */}
  <Button
    variant="contained"
    color="secondary"
    component={Link} to="/prijava"
    sx={{ mt: 2, padding: '6px 14px', borderRadius: '16px', fontSize: '0.8rem', fontWeight: 'bold' }} // Smaller button size
  >
    Follow
  </Button>
</Paper>



          {/* Rabbit Post Cards */}
        <Grid container spacing={3} sx={{ mt: 0 }}>
        {rabbitPosts.map((post, index) => (
          <Grid item xs={12} sm={6} md={3} key={index}>
            <Link to={`/objavaPrikaz/${post.id}`} style={{ textDecoration: 'none' }}>
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
                <Link to={`/profilKorisnika/${post.korisnicko_ime}`} style={{ textDecoration: 'none' }}>
                  <Typography sx={{ fontWeight: 'bold' }}>
                    {post.korisnicko_ime}
                  </Typography>
                </Link>
                <Typography variant="body2" color="textSecondary" sx={{ fontStyle: 'italic', whiteSpace: 'normal', wordWrap: 'break-word' }}>
                  {post.opis}
                </Typography>
              </Box>

            </Paper>
            </Link>
          </Grid>
        ))}
          </Grid>
        </Box>

    </div>
  );
}
