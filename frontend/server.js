const express = require('express');
const path = require('path');
const app = express();
const port = 3000;

// Configuration
// Toggle between API and local mock data
// When true, the server will call the backend REST API instead of using in-memory mock data.
const USE_API = true;

// Base URL for the backend API. Adjust the port if your backend runs on a different port.
const API_BASE_URL = 'http://localhost:8080/api';

/**
 * Convert a single object's keys from camelCase to snake_case.
 * The UI templates expect snake_case field names.
 * @param {Object} obj
 * @returns {Object}
 */
const toSnakeCase = (obj) => {
    if (!obj || typeof obj !== 'object') return obj;
    const newObj = {};
    for (const key of Object.keys(obj)) {
        const newKey = key.replace(/([A-Z])/g, '_$1').toLowerCase();
        newObj[newKey] = obj[key];
    }
    return newObj;
};

/**
 * Map an array of objects to snake_case.
 * @param {Array} arr
 * @returns {Array}
 */
const mapToSnakeCase = (arr) => Array.isArray(arr) ? arr.map(toSnakeCase) : [];

/**
 * Generic API request helper. It wraps fetch() and handles JSON responses.
 * It throws an Error if the HTTP status is not ok or if the API response indicates failure.
 * @param {string} method HTTP method (GET, POST, PUT, DELETE)
 * @param {string} endpoint Endpoint path starting with '/'
 * @param {Object|null} body Optional request payload
 * @returns {Promise<any>} Parsed data from the API response
 */
async function apiRequest(method, endpoint, body = null) {
    const options = { method, headers: { 'Content-Type': 'application/json' } };
    if (body) {
        options.body = JSON.stringify(body);
    }
    const response = await fetch(`${API_BASE_URL}${endpoint}`, options);
    let json;
    try {
        json = await response.json();
    } catch (err) {
        json = { success: false, message: 'Invalid JSON response' };
    }
    // If the HTTP status is not in the 2xx range or the API signals failure, throw an error.
    if (!response.ok || !json.success) {
        const errorMessage = (json && json.message) || `Request failed with status ${response.status}`;
        const error = new Error(errorMessage);
        error.status = response.status;
        error.apiResponse = json;
        throw error;
    }
    return json.data;
}

// View engine setup
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

// Serve static files
app.use(express.static(path.join(__dirname, 'public')));

// Middleware to parse body
app.use(express.urlencoded({ extended: true }));

// Helpers
const getNextId = (arr, idField) => {
    return arr.length > 0 ? Math.max(...arr.map(item => item[idField])) + 1 : 1;
};

// Date formatter (DD-MM-YYYY)
const formatDate = (dateString, fallback = '') => {
    if (!dateString) return fallback;
    const d = new Date(dateString);
    if (isNaN(d.getTime())) return dateString;
    const day = String(d.getDate()).padStart(2, '0');
    const month = String(d.getMonth() + 1).padStart(2, '0');
    const year = d.getFullYear();
    return `${day}-${month}-${year}`;
};

// Make helper available globally
app.use((req, res, next) => {
    res.locals.formatDate = formatDate;
    next();
});

// Mock Data
// Used when USE_API is false
let memberships = [
    { membership_id: 1, type: 'Silver', duration: 30, price: 300 },
    { membership_id: 2, type: 'Gold', duration: 90, price: 800 },
    { membership_id: 3, type: 'Platinum', duration: 365, price: 2500 }
];

let members = [
    { member_id: 1, first_name: 'John', last_name: 'Doe', gender: 'M', date_of_birth: '1990-05-15', registration_date: '2025-01-10', membership_id: 2, phone: '555-0101', email: 'john@example.com' },
    { member_id: 2, first_name: 'Jane', last_name: 'Smith', gender: 'F', date_of_birth: '1985-08-22', registration_date: '2025-02-01', membership_id: 1, phone: '555-0102', email: 'jane@example.com' },
    { member_id: 3, first_name: 'Ali', last_name: 'Veli', gender: 'M', date_of_birth: '2000-01-10', registration_date: '2025-03-05', membership_id: 3, phone: '555-0103', email: 'ali@example.com' }
];

let trainers = [
    { trainer_id: 1, first_name: 'Mike', last_name: 'Tyson', specialization: 'Boxing', phone: '555-9999', email: 'mike@gym.com' },
    { trainer_id: 2, first_name: 'Sarah', last_name: 'Connor', specialization: 'Cardio', phone: '555-8888', email: 'sarah@gym.com' }
];

let classes = [
    { class_id: 1, class_name: 'Morning Boxing', schedule: '2025-12-01', capacity: 20, trainer_id: 1 },
    { class_id: 2, class_name: 'Evening Yoga', schedule: '2025-12-01', capacity: 15, trainer_id: 2 }
];

let enrollments = [
    { enrollment_id: 1, member_id: 1, class_id: 1, enrollment_date: '2025-12-01' },
    { enrollment_id: 2, member_id: 2, class_id: 1, enrollment_date: '2025-12-02' }
];

let payments = [
    { payment_id: 1, member_id: 1, amount: 800, payment_date: '2025-11-20', payment_method: 'Credit Card' },
    { payment_id: 2, member_id: 2, amount: 300, payment_date: '2025-11-21', payment_method: 'Cash' }
];

// Routes

app.get('/', (req, res) => {
    res.redirect('/login');
});

app.get('/login', (req, res) => {
    res.render('pages/login');
});

// Dashboard
app.get('/dashboard', async (req, res) => {
    let statsData, activityData;

    if (USE_API) {
        // When USE_API is true, fetch statistics and recent activity from the backend API.
        try {
            // Fetch all required datasets in parallel
            const [membersData, classesData, paymentsData, enrollmentsData] = await Promise.all([
                apiRequest('GET', '/members'),
                apiRequest('GET', '/classes'),
                apiRequest('GET', '/payments'),
                apiRequest('GET', '/enrollments')
            ]);
            const memberList = mapToSnakeCase(membersData);
            const classList = mapToSnakeCase(classesData);
            const paymentList = mapToSnakeCase(paymentsData);
            const enrollmentList = mapToSnakeCase(enrollmentsData);
            // Build dashboard stats
            statsData = {
                totalMembers: memberList.length,
                activeClasses: classList.length,
                monthlyRevenue: paymentList.reduce((sum, p) => sum + (parseFloat(p.amount) || 0), 0)
            };
            // Recent payments: sort by payment_date desc then payment_id desc
            const recentPayments = [...paymentList]
                .sort((a, b) => new Date(b.payment_date) - new Date(a.payment_date) || b.payment_id - a.payment_id)
                .slice(0, 5)
                .map(p => ({
                    type: 'Payment',
                    date: formatDate(p.payment_date),
                    text: `${p.member_name || 'Unknown Member'} paid $${p.amount}`
                }));
            // Recent enrollments: sort by enrollment_date desc then enrollment_id desc
            const recentEnrollments = [...enrollmentList]
                .sort((a, b) => new Date(b.enrollment_date) - new Date(a.enrollment_date) || b.enrollment_id - a.enrollment_id)
                .slice(0, 5)
                .map(e => ({
                    type: 'Enrollment',
                    date: formatDate(e.enrollment_date),
                    text: `${e.member_name || 'Unknown Member'} enrolled in ${e.class_name || 'Class'}`
                }));
            // Combine both and limit overall to 5 items
            activityData = [...recentPayments, ...recentEnrollments].slice(0, 5);
        } catch (error) {
            // On any failure, log and fall back to empty stats and activity lists
            console.error('Error fetching dashboard data:', error);
            statsData = { totalMembers: 0, activeClasses: 0, monthlyRevenue: 0 };
            activityData = [];
        }
    } else {
        // Mock data logic
        statsData = {
            totalMembers: members.length,
            activeClasses: classes.length,
            monthlyRevenue: payments.reduce((acc, p) => acc + p.amount, 0)
        };

        const recentPayments = [...payments]
            .sort((a, b) => new Date(b.payment_date) - new Date(a.payment_date) || b.payment_id - a.payment_id)
            .slice(0, 5)
            .map(p => {
                const member = members.find(m => m.member_id === p.member_id);
                return {
                    type: 'Payment',
                    date: formatDate(p.payment_date),
                    text: `${member ? member.first_name + ' ' + member.last_name : 'Unknown Member'} paid $${p.amount}`
                };
            });

        const recentEnrollments = [...enrollments]
            .sort((a, b) => new Date(b.enrollment_date) - new Date(a.enrollment_date) || b.enrollment_id - a.enrollment_id)
            .slice(0, 5)
            .map(e => {
                const member = members.find(m => m.member_id === e.member_id);
                const cls = classes.find(c => c.class_id === e.class_id);
                return {
                    type: 'Enrollment',
                    date: formatDate(e.enrollment_date),
                    text: `${member ? member.first_name + ' ' + member.last_name : 'Unknown Member'} enrolled in ${cls ? cls.class_name : 'Class'}`
                };
            });

        activityData = [...recentPayments, ...recentEnrollments].slice(0, 5);
    }

    res.render('pages/dashboard', {
        title: 'Dashboard',
        path: '/dashboard',
        stats: statsData,
        recentActivity: activityData
    });
});

// Members
app.get('/members', async (req, res) => {
    const error = req.query.error || null;
    const success = req.query.success || null;
    let localMembers, localMemberships;

    if (USE_API) {
        try {
            // Fetch members and memberships from API
            const [membersData, membershipsData] = await Promise.all([
                apiRequest('GET', '/members'),
                apiRequest('GET', '/memberships')
            ]);
            localMembers = mapToSnakeCase(membersData);
            localMemberships = mapToSnakeCase(membershipsData);
        } catch (error) {
            console.error('Error fetching members:', error);
            localMembers = [];
            localMemberships = [];
        }
    } else {
        localMembers = members;
        localMemberships = memberships;
    }

    res.render('pages/members', {
        title: 'Members',
        path: '/members',
        members: localMembers,
        memberships: localMemberships,
        error,
        success
    });
});

app.post('/members/create', async (req, res) => {
    if (USE_API) {
        // Create a new member via the backend API
        const { first_name, last_name, gender, date_of_birth, membership_id, phone, email } = req.body;
        const payload = {
            firstName: first_name,
            lastName: last_name,
            gender,
            dateOfBirth: date_of_birth,
            membershipId: membership_id ? parseInt(membership_id) : null,
            phone,
            email
        };
        try {
            await apiRequest('POST', '/members', payload);
            return res.redirect('/members?success=Created Member Successfully');
        } catch (error) {
            // Use API-provided message if available, otherwise generic error
            const message = error.status === 409 ? (error.apiResponse && error.apiResponse.message) : (error.message || 'Failed to create member');
            return res.redirect(`/members?error=${encodeURIComponent(message)}`);
        }
    } else {
        // Fallback to mock data when API is disabled
        const { first_name, last_name, gender, date_of_birth, registration_date, membership_id, phone, email } = req.body;
        const newItem = {
            member_id: getNextId(members, 'member_id'),
            first_name, last_name, gender, date_of_birth,
            registration_date: registration_date || new Date().toISOString().split('T')[0],
            membership_id: parseInt(membership_id),
            phone, email
        };
        members.push(newItem);
        return res.redirect('/members?success=Created Member Successfully');
    }
});

app.post('/members/update', async (req, res) => {
    if (USE_API) {
        // Update an existing member via the backend API
        const { member_id, first_name, last_name, gender, date_of_birth, membership_id, phone, email } = req.body;
        const payload = {
            firstName: first_name,
            lastName: last_name,
            gender,
            dateOfBirth: date_of_birth,
            membershipId: membership_id ? parseInt(membership_id) : null,
            phone,
            email
        };
        try {
            await apiRequest('PUT', `/members/${member_id}`, payload);
            return res.redirect('/members?success=Updated Member Successfully');
        } catch (error) {
            const message = error.status === 409 ? (error.apiResponse && error.apiResponse.message) : (error.message || 'Failed to update member');
            return res.redirect(`/members?error=${encodeURIComponent(message)}`);
        }
    } else {
        const { member_id, first_name, last_name, gender, date_of_birth, registration_date, membership_id, phone, email } = req.body;
        const index = members.findIndex(m => m.member_id == member_id);
        if (index !== -1) {
            members[index] = {
                ...members[index],
                first_name, last_name, gender, date_of_birth,
                registration_date,
                membership_id: parseInt(membership_id),
                phone, email
            };
        }
        return res.redirect('/members?success=Updated Member Successfully');
    }
});

app.post('/members/delete', async (req, res) => {
    const { member_id } = req.body;
    if (USE_API) {
        // Delete a member via the backend API
        try {
            await apiRequest('DELETE', `/members/${member_id}`);
            return res.redirect('/members?success=Deleted Member Successfully');
        } catch (error) {
            const message = (error.apiResponse && error.apiResponse.message) || error.message || 'Failed to delete member';
            return res.redirect(`/members?error=${encodeURIComponent(message)}`);
        }
    } else {
        // Check constraints before delete when using mock data
        const hasEnrollments = enrollments.some(e => e.member_id == member_id);
        const hasPayments = payments.some(p => p.member_id == member_id);

        if (hasEnrollments || hasPayments) {
            return res.redirect('/members?error=Cannot delete member with associated enrollments or payments.');
        }
        members = members.filter(m => m.member_id != member_id);
        return res.redirect('/members?success=Deleted Member Successfully');
    }
});

// Memberships
app.get('/memberships', async (req, res) => {
    const error = req.query.error || null;
    const success = req.query.success || null;
    let localMemberships;

    if (USE_API) {
        try {
            const data = await apiRequest('GET', '/memberships');
            localMemberships = mapToSnakeCase(data);
        } catch (error) {
            console.error('Error fetching memberships:', error);
            localMemberships = [];
        }
    } else {
        localMemberships = memberships;
    }

    res.render('pages/memberships', {
        title: 'Memberships',
        path: '/memberships',
        memberships: localMemberships,
        error,
        success
    });
});

app.post('/memberships/create', async (req, res) => {
    if (USE_API) {
        const { type, duration, price } = req.body;
        const payload = {
            type,
            duration: duration ? parseInt(duration) : null,
            price: price ? parseFloat(price) : null
        };
        try {
            await apiRequest('POST', '/memberships', payload);
            return res.redirect('/memberships?success=Created Membership Successfully');
        } catch (error) {
            const message = (error.apiResponse && error.apiResponse.message) || error.message || 'Failed to create membership';
            return res.redirect(`/memberships?error=${encodeURIComponent(message)}`);
        }
    } else {
        const { type, duration, price } = req.body;
        memberships.push({
            membership_id: getNextId(memberships, 'membership_id'),
            type,
            duration: parseInt(duration),
            price: parseFloat(price)
        });
        return res.redirect('/memberships?success=Created Membership Successfully');
    }
});

app.post('/memberships/update', async (req, res) => {
    if (USE_API) {
        const { membership_id, type, duration, price } = req.body;
        const payload = {
            type,
            duration: duration ? parseInt(duration) : null,
            price: price ? parseFloat(price) : null
        };
        try {
            await apiRequest('PUT', `/memberships/${membership_id}`, payload);
            return res.redirect('/memberships?success=Updated Membership Successfully');
        } catch (error) {
            const message = (error.apiResponse && error.apiResponse.message) || error.message || 'Failed to update membership';
            return res.redirect(`/memberships?error=${encodeURIComponent(message)}`);
        }
    } else {
        const { membership_id, type, duration, price } = req.body;
        const index = memberships.findIndex(m => m.membership_id == membership_id);
        if (index !== -1) {
            memberships[index] = { ...memberships[index], type, duration: parseInt(duration), price: parseFloat(price) };
        }
        return res.redirect('/memberships?success=Updated Membership Successfully');
    }
});

app.post('/memberships/delete', async (req, res) => {
    const { membership_id } = req.body;
    if (USE_API) {
        try {
            await apiRequest('DELETE', `/memberships/${membership_id}`);
            return res.redirect('/memberships?success=Deleted Membership Successfully');
        } catch (error) {
            const message = (error.apiResponse && error.apiResponse.message) || error.message || 'Failed to delete membership';
            return res.redirect(`/memberships?error=${encodeURIComponent(message)}`);
        }
    } else {
        const isUsed = members.some(m => m.membership_id == membership_id);
        if (isUsed) {
            return res.redirect('/memberships?error=Cannot delete membership assigned to members.');
        }
        memberships = memberships.filter(m => m.membership_id != membership_id);
        return res.redirect('/memberships?success=Deleted Membership Successfully');
    }
});

// Trainers
app.get('/trainers', async (req, res) => {
    const error = req.query.error || null;
    const success = req.query.success || null;
    let localTrainers;

    if (USE_API) {
        try {
            const data = await apiRequest('GET', '/trainers');
            localTrainers = mapToSnakeCase(data);
        } catch (error) {
            console.error('Error fetching trainers:', error);
            localTrainers = [];
        }
    } else {
        localTrainers = trainers;
    }

    res.render('pages/trainers', {
        title: 'Trainers',
        path: '/trainers',
        trainers: localTrainers,
        error,
        success
    });
});

app.post('/trainers/create', async (req, res) => {
    if (USE_API) {
        const { first_name, last_name, specialization, phone, email } = req.body;
        const payload = {
            firstName: first_name,
            lastName: last_name,
            specialization,
            phone,
            email
        };
        try {
            await apiRequest('POST', '/trainers', payload);
            return res.redirect('/trainers?success=Created Trainer Successfully');
        } catch (error) {
            const message = (error.apiResponse && error.apiResponse.message) || error.message || 'Failed to create trainer';
            return res.redirect(`/trainers?error=${encodeURIComponent(message)}`);
        }
    } else {
        const { first_name, last_name, specialization, phone, email } = req.body;
        const newItem = {
            trainer_id: getNextId(trainers, 'trainer_id'),
            first_name, last_name, specialization, phone, email
        };
        trainers.push(newItem);
        return res.redirect('/trainers?success=Created Trainer Successfully');
    }
});

app.post('/trainers/update', async (req, res) => {
    if (USE_API) {
        const { trainer_id, first_name, last_name, specialization, phone, email } = req.body;
        const payload = {
            firstName: first_name,
            lastName: last_name,
            specialization,
            phone,
            email
        };
        try {
            await apiRequest('PUT', `/trainers/${trainer_id}`, payload);
            return res.redirect('/trainers?success=Updated Trainer Successfully');
        } catch (error) {
            const message = (error.apiResponse && error.apiResponse.message) || error.message || 'Failed to update trainer';
            return res.redirect(`/trainers?error=${encodeURIComponent(message)}`);
        }
    } else {
        const { trainer_id, first_name, last_name, specialization, phone, email } = req.body;
        const index = trainers.findIndex(t => t.trainer_id == trainer_id);
        if (index !== -1) {
            trainers[index] = { ...trainers[index], first_name, last_name, specialization, phone, email };
        }
        return res.redirect('/trainers?success=Updated Trainer Successfully');
    }
});

app.post('/trainers/delete', async (req, res) => {
    const { trainer_id } = req.body;
    if (USE_API) {
        try {
            await apiRequest('DELETE', `/trainers/${trainer_id}`);
            return res.redirect('/trainers?success=Deleted Trainer Successfully');
        } catch (error) {
            const message = (error.apiResponse && error.apiResponse.message) || error.message || 'Failed to delete trainer';
            return res.redirect(`/trainers?error=${encodeURIComponent(message)}`);
        }
    } else {
        const isUsed = classes.some(c => c.trainer_id == trainer_id);
        if (isUsed) {
            return res.redirect('/trainers?error=Cannot delete trainer assigned to classes.');
        }
        trainers = trainers.filter(t => t.trainer_id != trainer_id);
        return res.redirect('/trainers?success=Deleted Trainer Successfully');
    }
});

// Classes
app.get('/classes', async (req, res) => {
    const error = req.query.error || null;
    const success = req.query.success || null;
    let localClasses, localTrainers;

    if (USE_API) {
        try {
            const [classesData, trainersData] = await Promise.all([
                apiRequest('GET', '/classes'),
                apiRequest('GET', '/trainers')
            ]);
            localClasses = mapToSnakeCase(classesData);
            localTrainers = mapToSnakeCase(trainersData);
        } catch (error) {
            console.error('Error fetching classes or trainers:', error);
            localClasses = [];
            localTrainers = [];
        }
    } else {
        localClasses = classes;
        localTrainers = trainers;
    }

    res.render('pages/classes', {
        title: 'Classes',
        path: '/classes',
        classes: localClasses,
        trainers: localTrainers,
        error,
        success
    });
});

app.post('/classes/create', async (req, res) => {
    const { class_name, schedule, capacity, trainer_id } = req.body;
    if (USE_API) {
        const payload = {
            className: class_name,
            schedule,
            capacity: capacity ? parseInt(capacity) : null,
            trainerId: trainer_id ? parseInt(trainer_id) : null
        };
        try {
            await apiRequest('POST', '/classes', payload);
            return res.redirect('/classes?success=Created Class Successfully');
        } catch (error) {
            const message = (error.apiResponse && error.apiResponse.message) || error.message || 'Failed to create class';
            return res.redirect(`/classes?error=${encodeURIComponent(message)}`);
        }
    } else {
        const trainerExists = trainers.some(t => t.trainer_id == trainer_id);
        if (!trainerExists) {
            return res.redirect('/classes?error=Invalid Trainer Selected');
        }
        const newItem = {
            class_id: getNextId(classes, 'class_id'),
            class_name,
            schedule,
            capacity: parseInt(capacity) || 0,
            trainer_id: parseInt(trainer_id)
        };
        classes.push(newItem);
        return res.redirect('/classes?success=Created Class Successfully');
    }
});

app.post('/classes/update', async (req, res) => {
    const { class_id, class_name, schedule, capacity, trainer_id } = req.body;
    if (USE_API) {
        const payload = {
            className: class_name,
            schedule,
            capacity: capacity ? parseInt(capacity) : null,
            trainerId: trainer_id ? parseInt(trainer_id) : null
        };
        try {
            await apiRequest('PUT', `/classes/${class_id}`, payload);
            return res.redirect('/classes?success=Updated Class Successfully');
        } catch (error) {
            const message = (error.apiResponse && error.apiResponse.message) || error.message || 'Failed to update class';
            return res.redirect(`/classes?error=${encodeURIComponent(message)}`);
        }
    } else {
        const index = classes.findIndex(c => c.class_id == class_id);
        if (index !== -1) {
            classes[index] = {
                ...classes[index],
                class_name,
                schedule,
                capacity: parseInt(capacity) || 0,
                trainer_id: parseInt(trainer_id)
            };
        }
        return res.redirect('/classes?success=Updated Class Successfully');
    }
});

app.post('/classes/delete', async (req, res) => {
    const { class_id } = req.body;
    if (USE_API) {
        try {
            await apiRequest('DELETE', `/classes/${class_id}`);
            return res.redirect('/classes?success=Deleted Class Successfully');
        } catch (error) {
            const message = (error.apiResponse && error.apiResponse.message) || error.message || 'Failed to delete class';
            return res.redirect(`/classes?error=${encodeURIComponent(message)}`);
        }
    } else {
        const isUsed = enrollments.some(e => e.class_id == class_id);
        if (isUsed) {
            return res.redirect('/classes?error=Cannot delete class with active enrollments.');
        }
        classes = classes.filter(c => c.class_id != class_id);
        return res.redirect('/classes?success=Deleted Class Successfully');
    }
});

// Enrollments
app.get('/enrollments', async (req, res) => {
    const error = req.query.error || null;
    const success = req.query.success || null;
    let localEnrollments, localMembers, localClasses;

    if (USE_API) {
        try {
            const [enrollmentsData, membersData, classesData] = await Promise.all([
                apiRequest('GET', '/enrollments'),
                apiRequest('GET', '/members'),
                apiRequest('GET', '/classes')
            ]);
            localEnrollments = mapToSnakeCase(enrollmentsData);
            localMembers = mapToSnakeCase(membersData);
            localClasses = mapToSnakeCase(classesData);
        } catch (error) {
            console.error('Error fetching enrollments:', error);
            localEnrollments = [];
            localMembers = [];
            localClasses = [];
        }
    } else {
        localEnrollments = enrollments;
        localMembers = members;
        localClasses = classes;
    }

    res.render('pages/enrollments', {
        title: 'Enrollments',
        path: '/enrollments',
        enrollments: localEnrollments,
        members: localMembers,
        classes: localClasses,
        error,
        success
    });
});

app.post('/enrollments/create', async (req, res) => {
    const { member_id, class_id, enrollment_date } = req.body;
    if (USE_API) {
        const payload = {
            memberId: member_id ? parseInt(member_id) : null,
            classId: class_id ? parseInt(class_id) : null,
            enrollmentDate: enrollment_date
        };
        try {
            await apiRequest('POST', '/enrollments/create', payload);
            return res.redirect('/enrollments?success=Member Enrolled Successfully');
        } catch (error) {
            const message = (error.apiResponse && error.apiResponse.message) || error.message || 'Failed to enroll member';
            return res.redirect(`/enrollments?error=${encodeURIComponent(message)}`);
        }
    } else {
        const memberExists = members.some(m => m.member_id == member_id);
        const classExists = classes.some(c => c.class_id == class_id);

        if (!memberExists || !classExists) {
            return res.redirect('/enrollments?error=Invalid Member or Class');
        }

        const exists = enrollments.some(e => e.member_id == member_id && e.class_id == class_id);
        if (exists) {
            return res.redirect('/enrollments?error=Member is already enrolled in this class');
        }

        const newItem = {
            enrollment_id: getNextId(enrollments, 'enrollment_id'),
            member_id: parseInt(member_id),
            class_id: parseInt(class_id),
            enrollment_date: enrollment_date || new Date().toISOString().split('T')[0]
        };
        enrollments.push(newItem);
        return res.redirect('/enrollments?success=Member Enrolled Successfully');
    }
});

app.post('/enrollments/delete', async (req, res) => {
    const { enrollment_id } = req.body;
    if (USE_API) {
        try {
            await apiRequest('DELETE', `/enrollments/${enrollment_id}`);
            return res.redirect('/enrollments?success=Enrollment Removed Successfully');
        } catch (error) {
            const message = (error.apiResponse && error.apiResponse.message) || error.message || 'Failed to delete enrollment';
            return res.redirect(`/enrollments?error=${encodeURIComponent(message)}`);
        }
    } else {
        enrollments = enrollments.filter(e => e.enrollment_id != enrollment_id);
        return res.redirect('/enrollments?success=Enrollment Removed Successfully');
    }
});

// Payments
app.get('/payments', async (req, res) => {
    const error = req.query.error || null;
    const success = req.query.success || null;
    let localPayments, localMembers;

    if (USE_API) {
        try {
            const [paymentsData, membersData] = await Promise.all([
                apiRequest('GET', '/payments'),
                apiRequest('GET', '/members')
            ]);
            localPayments = mapToSnakeCase(paymentsData);
            localMembers = mapToSnakeCase(membersData);
        } catch (error) {
            console.error('Error fetching payments:', error);
            localPayments = [];
            localMembers = [];
        }
    } else {
        localPayments = payments;
        localMembers = members;
    }

    res.render('pages/payments', {
        title: 'Payments',
        path: '/payments',
        payments: localPayments,
        members: localMembers,
        error,
        success
    });
});

app.post('/payments/create', async (req, res) => {
    const { member_id, amount, payment_date, payment_method } = req.body;
    if (USE_API) {
        const payload = {
            memberId: member_id ? parseInt(member_id) : null,
            amount: amount ? parseFloat(amount) : null,
            paymentDate: payment_date,
            paymentMethod: payment_method
        };
        try {
            await apiRequest('POST', '/payments/create', payload);
            return res.redirect('/payments?success=Payment Recorded Successfully');
        } catch (error) {
            const message = (error.apiResponse && error.apiResponse.message) || error.message || 'Failed to record payment';
            return res.redirect(`/payments?error=${encodeURIComponent(message)}`);
        }
    } else {
        const memberExists = members.some(m => m.member_id == member_id);
        if (!memberExists) return res.redirect('/payments?error=Invalid Member');
        const newItem = {
            payment_id: getNextId(payments, 'payment_id'),
            member_id: parseInt(member_id),
            amount: parseFloat(amount),
            payment_date: payment_date || new Date().toISOString().split('T')[0],
            payment_method
        };
        payments.push(newItem);
        return res.redirect('/payments?success=Payment Recorded Successfully');
    }
});

app.post('/payments/update', async (req, res) => {
    const { payment_id, member_id, amount, payment_date, payment_method } = req.body;
    if (USE_API) {
        const payload = {
            memberId: member_id ? parseInt(member_id) : null,
            amount: amount ? parseFloat(amount) : null,
            paymentDate: payment_date,
            paymentMethod: payment_method
        };
        try {
            await apiRequest('PUT', `/payments/${payment_id}`, payload);
            return res.redirect('/payments?success=Payment Updated Successfully');
        } catch (error) {
            const message = (error.apiResponse && error.apiResponse.message) || error.message || 'Failed to update payment';
            return res.redirect(`/payments?error=${encodeURIComponent(message)}`);
        }
    } else {
        const index = payments.findIndex(p => p.payment_id == payment_id);
        if (index !== -1) {
            payments[index] = {
                ...payments[index],
                member_id: parseInt(member_id),
                amount: parseFloat(amount),
                payment_date,
                payment_method
            };
        }
        return res.redirect('/payments?success=Payment Updated Successfully');
    }
});

app.post('/payments/delete', async (req, res) => {
    const { payment_id } = req.body;
    if (USE_API) {
        try {
            await apiRequest('DELETE', `/payments/${payment_id}`);
            return res.redirect('/payments?success=Payment Deleted Successfully');
        } catch (error) {
            const message = (error.apiResponse && error.apiResponse.message) || error.message || 'Failed to delete payment';
            return res.redirect(`/payments?error=${encodeURIComponent(message)}`);
        }
    } else {
        payments = payments.filter(p => p.payment_id != payment_id);
        return res.redirect('/payments?success=Payment Deleted Successfully');
    }
});

// Reports
app.get('/reports', async (req, res) => {
    let spResult = null;
    if (req.query.sp_result) {
        try {
            spResult = JSON.parse(req.query.sp_result);
        } catch (e) {
            console.error("Invalid sp_result JSON:", e);
        }
    }
    const error = req.query.error || null;
    const success = req.query.success || null;

    let localMembers, reportData;

    if (USE_API) {
        try {
            // Fetch all members for selection list
            const membersData = await apiRequest('GET', '/members');
            localMembers = mapToSnakeCase(membersData);
            // Initialize report variables
            let oldestMember = null;
            let popularClass = null;
            let membershipCounts = {};
            let trainerWorkload = [];
            let totalRevenue = 0;
            let inactiveMembers = [];
            // Oldest member
            try {
                const om = await apiRequest('GET', '/reports/oldest-member');
                if (om) {
                    oldestMember = toSnakeCase(om);
                }
            } catch (e) {
                oldestMember = null;
            }
            // Most popular class
            try {
                const pc = await apiRequest('GET', '/reports/most-popular-class');
                if (pc) {
                    const snake = toSnakeCase(pc);
                    popularClass = { ...snake, count: snake.enrollment_count };
                    delete popularClass.enrollment_count;
                }
            } catch (e) {
                popularClass = null;
            }
            // Membership distribution
            try {
                const dist = await apiRequest('GET', '/reports/members-by-membership');
                const distList = mapToSnakeCase(dist);
                distList.forEach(item => {
                    membershipCounts[item.type] = item.member_count;
                });
            } catch (e) {
                membershipCounts = {};
            }
            // Trainer workload
            try {
                const tw = await apiRequest('GET', '/reports/trainer-workload');
                const twList = mapToSnakeCase(tw);
                trainerWorkload = twList.map(item => ({
                    name: `${item.first_name} ${item.last_name}`,
                    count: item.class_count
                }));
            } catch (e) {
                trainerWorkload = [];
            }
            // Total revenue from payments
            try {
                const pData = await apiRequest('GET', '/payments');
                const pList = mapToSnakeCase(pData);
                totalRevenue = pList.reduce((sum, p) => sum + (parseFloat(p.amount) || 0), 0);
            } catch (e) {
                totalRevenue = 0;
            }
            // Inactive members (no enrollments)
            try {
                const eData = await apiRequest('GET', '/enrollments');
                const eList = mapToSnakeCase(eData);
                const enrolledIds = new Set(eList.map(en => en.member_id));
                inactiveMembers = localMembers.filter(m => !enrolledIds.has(m.member_id));
            } catch (e) {
                inactiveMembers = [];
            }
            reportData = {
                oldestMember,
                popularClass,
                totalRevenue,
                membershipCounts,
                trainerWorkload,
                inactiveMembers
            };
        } catch (error) {
            console.error('Error generating reports:', error);
            localMembers = [];
            reportData = {
                oldestMember: null,
                popularClass: null,
                totalRevenue: 0,
                membershipCounts: {},
                trainerWorkload: [],
                inactiveMembers: []
            };
        }
    } else {
        localMembers = members;

        // Report calculations
        const oldestMember = [...members].sort((a, b) => new Date(a.date_of_birth) - new Date(b.date_of_birth))[0];

        const classCounts = {};
        enrollments.forEach(e => { classCounts[e.class_id] = (classCounts[e.class_id] || 0) + 1; });
        const sortedClasses = Object.entries(classCounts).sort((a, b) => b[1] - a[1]);
        const popularClassId = sortedClasses.length > 0 ? parseInt(sortedClasses[0][0]) : null;
        const popularClass = popularClassId ? classes.find(c => c.class_id === popularClassId) : null;
        const popularClassCount = sortedClasses.length > 0 ? sortedClasses[0][1] : 0;

        const totalRevenue = payments.reduce((sum, p) => sum + p.amount, 0);

        const membershipCounts = {};
        members.forEach(m => {
            const mName = memberships.find(ms => ms.membership_id === m.membership_id)?.type || 'Unknown';
            membershipCounts[mName] = (membershipCounts[mName] || 0) + 1;
        });

        const trainerWorkload = trainers.map(t => {
            const count = classes.filter(c => c.trainer_id === t.trainer_id).length;
            return { name: `${t.first_name} ${t.last_name}`, count };
        });

        const inactiveMembers = members.filter(m => !enrollments.some(e => e.member_id === m.member_id));

        reportData = {
            oldestMember,
            popularClass: popularClass ? { ...popularClass, count: popularClassCount } : null,
            totalRevenue,
            membershipCounts,
            trainerWorkload,
            inactiveMembers
        };
    }

    res.render('pages/reports', {
        title: 'Reports',
        path: '/reports',
        members: localMembers,
        reportData,
        spResult,
        error,
        success
    });
});

app.post('/reports/sp-demo', async (req, res) => {
    if (USE_API) {
        // Execute member payment summary via API for stored procedure demo
        const { member_id } = req.body;
        try {
            const data = await apiRequest('GET', `/reports/member-payment-summary?memberId=${member_id}`);
            if (!data) {
                return res.redirect('/reports');
            }
            const snake = toSnakeCase(data);
            const spResult = {
                member_name: snake.full_name,
                total_paid: parseFloat(snake.total_paid) || 0,
                payment_count: snake.payment_count,
                last_payment_date: snake.last_payment_date ? formatDate(snake.last_payment_date) : ''
            };
            return res.redirect(`/reports?sp_result=${encodeURIComponent(JSON.stringify(spResult))}`);
        } catch (error) {
            const message = (error.apiResponse && error.apiResponse.message) || error.message || 'Failed to execute function';
            return res.redirect(`/reports?error=${encodeURIComponent(message)}`);
        }
    } else {
        const { member_id } = req.body;
        const memberIdInt = parseInt(member_id);
        const member = members.find(m => m.member_id === memberIdInt);

        if (!member) {
            return res.redirect('/reports');
        }

        const memberPayments = payments.filter(p => p.member_id === memberIdInt);
        const totalPaid = memberPayments.reduce((sum, p) => sum + p.amount, 0);
        const paymentCount = memberPayments.length;
        const lastPayment = memberPayments.length > 0
            ? memberPayments.sort((a, b) => new Date(b.payment_date) - new Date(a.payment_date))[0].payment_date
            : 'N/A';

        const result = {
            member_name: `${member.first_name} ${member.last_name}`,
            total_paid: totalPaid,
            payment_count: paymentCount,
            last_payment_date: formatDate(lastPayment)
        };
        return res.redirect(`/reports?sp_result=${encodeURIComponent(JSON.stringify(result))}`);
    }
    // Fallback if use_api is true but nothing implemented
    res.redirect('/reports');
});

app.listen(port, () => {
    console.log(`Gym Management System running at http://localhost:${port}`);
});
