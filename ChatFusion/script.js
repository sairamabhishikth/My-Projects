
const chatInput = document.querySelector("#chat-input");
const sendButton = document.querySelector("#send-btn");
const chatContainer = document.querySelector(".chat-container");
let userText = '';
let customInstructions = ""; // Initialize at the top of your JS

const API_KEY = "Enter your API KEY"; 

const newChatButton = document.querySelector("#new-chat-btn");

let showAds = true;

// Add an event listener to the toggle button
const toggleAdsButton = document.querySelector("#toggleAdsButton");
toggleAdsButton.addEventListener("change", () => {
    showAds = toggleAdsButton.checked;
    updateAdsStatus();
});

// Function to update the ads status text
function updateAdsStatus() {
    const adsStatus = document.querySelector("#adsStatus");
    adsStatus.textContent = showAds ? "Ads: ON" : "Ads: OFF";
}
const modalResponseText = document.getElementById('responseText').value;

newCustomInstructions = modalResponseText;
sessionStorage.setItem('customInstructions', newCustomInstructions);
// Function to reset the chat and load initial data
const resetChat = () => {
    // Clear the chat history in the middle container
    chatContainer.innerHTML = "";
    const customInstructions = sessionStorage.getItem('customInstructions');

    // Set context
    activeContexts = [customInstructions];
    // Clear local storage for chat history
    localStorage.removeItem("sidebar-chat-history");

    // Reset the userText
    userText = "";

    // Reset the input field
    chatInput.value = "";

    // Load initial data
    loadDataFromLocalstorage();
};

// Event listener for the "New Chat" button
newChatButton.addEventListener("click", resetChat);

function openInstructionsModal() {
    // Get the modal element
    const modal = document.getElementById("userInstructionsModal");
    // Display the modal by setting its style to "block"
    modal.style.display = "block";

    // Add event listeners for the close button and cancel button
    const closeButton = document.getElementById("closeButton");
    const cancelButton = document.getElementById("cancelButton");
    const SaveButton = document.getElementById("SaveButton");
    // Close the modal when the "Close" button or "Cancel" button is clicked
    closeButton.addEventListener("click", () => {
        modal.style.display = "none";
    });

    cancelButton.addEventListener("click", () => {
        modal.style.display = "none";
    });
    SaveButton.addEventListener("click", () => {
        const responseTextArea = document.getElementById('responseText');
        const newCustomInstructions = responseTextArea.value;
    
        // Save the new custom instructions in localStorage
        localStorage.setItem('customInstructions', newCustomInstructions);
    
        // Hide the modal
        modal.style.display = "none";
    });
    
    

    // You can add further customization or functionality here as needed
}

// Check if the user is an admin (replace with your actual logic)
const isAdmin = sessionStorage.getItem('adminLoggedIn') === 'true';

// Display the "Custom Instructions" button and admin section only if the user is an admin
const customInstructionsButton = document.getElementById('custominstructionsbutton');
const adminSection = document.getElementById('adminSection');

if (isAdmin) {
    customInstructionsButton.style.display = 'block';
    adminSection.style.display = 'block';
}

// Add an event listener to open the custom instructions modal when the button is clicked
customInstructionsButton.addEventListener('click', openInstructionsModal);

// Update the loadDataFromLocalstorage function
const loadDataFromLocalstorage = () => {
    // Clear the chat history in the middle container
    chatContainer.innerHTML = "";

    // Load the chat history from the sidebar's local storage and append it to the chat container
    const defaultText = `<div class="default-text">
                            <h1>Chatfusion</h1>
                            <p>Start a conversation and explore the power of AI.<br> Your chat history will be displayed here.</p>
                        </div>`;
    const sidebarChatHistory = localStorage.getItem("sidebar-chat-history") || defaultText;
    chatContainer.innerHTML = sidebarChatHistory;

    // Scroll to the bottom of the chat container
    chatContainer.scrollTo(0, chatContainer.scrollHeight);

    // Reset the initial state
    handleUserQueryClick("InitialQuery");
};

// ...
document.addEventListener('DOMContentLoaded', () => {
    // Retrieve the custom instructions from sessionStorage
    const customInstructions = sessionStorage.getItem('customInstructions');

    if (customInstructions) {
        // Use the customInstructions to display or integrate them into the UI
        const customInstructionsElement = document.getElementById('customInstructionsElement');
        customInstructionsElement.textContent = customInstructions;
        // You can modify the display of this element based on your UI requirements
        customInstructionsElement.style.backgroundColor = '#f0f0f0';
        customInstructionsElement.style.padding = '10px';
        // Adjust the styles as needed to suit your design
    }

    // Your other chat-related logic and event listeners can go here...
    // Ensure that the retrieved custom instructions are used in the chat functionality
});

const generateAdvertisement = (userText) => {
    userText = userText.toLowerCase();

    // Example: Generate advertisements with product information and direct product links based on user queries
    if (userText.includes("fruits")) {
        const productInfo = [
            {
                name: "Apple",
                price: "$1.30",
                quantity: "1",
                imageSrc: "images/fruits_walmart.jpg",
                link: "https://www.walmart.com/ip/Fresh-Honeycrisp-Apple-Each/44390950"
            },
            {
                name: "Banana",
                price: "$0.19",
                quantity: "1",
                imageSrc: "images/fruits_amazon.jpeg",
                link: "https://www.walmart.com/ip/Marketside-Fresh-Organic-Bananas-Bunch/51259338?from=/search"
            },
            {
                name: "Grapes",
                price: "$4.91",
                quantity: "1",
                imageSrc: "images/grapes.jpeg",
                link: "https://www.walmart.com/ip/Fresh-Green-Seedless-Grapes/44390943?from=/search"
            }
        ];

        // Generate HTML code for the advertisements with clickable links
        let advertisementHtml = "<div class=\"advertisement-container\">";
    for (let i = 0; i < productInfo.length; i++) {
        advertisementHtml += `
            <div class="product">
                <a href="${productInfo[i].link}" class="product-link" target="_blank">
                    <div class="product-image">
                    <img src="${productInfo[i].imageSrc}" alt="${productInfo[i].name}" class="image-large" width="300" height="300">
                    </div>
                    <div class="product-details">
                        <h3>${productInfo[i].name}</h3>
                        <p>Price: ${productInfo[i].price}</p>
                        <p>Quantity: ${productInfo[i].quantity}</p>
                        <a class="product-price-link" href="${productInfo[i].link}" target="_blank">View Details</a>
                    </div>
                </a>
            </div>`;
    }
    advertisementHtml += "</div>";

    return advertisementHtml;
    } else if (userText.includes("cold")) {
        const productInfo = [
            {
                name: "Covonia Dry",
                price: "$22.10",
                quantity: "150 ml",
                imageSrc: "images/walmart.jpeg",
                link: "https://www.walmart.com/ip/Covonia-Dry-Tickly-Cough-Linctus-Mixture-Syrup-150ml/3351565255?from=/search"
            },
            {
                name: "Walgreens",
                price: "$12.99",
                quantity: "0.83 oz",
                imageSrc: "images/walgreens.jpeg",
                link: "https://www.walgreens.com/store/c/boiron-chestal-honey-adult-cough-syrup-homeopathic-medicine/ID=prod6311732-product"
            },
            {
                name: "Robitussin",
                price: "$12.99",
                quantity: "0.83 oz",
                imageSrc: "images/CVS.jpeg",
                link: "https://www.cvs.com/shop/robitussin-adult-cough-chest-congestion-dm-non-drowsy-cough-suppressant-expectorant-prodid-1040192"
            }
        ];

        // Generate HTML code for the advertisements with clickable links
        let advertisementHtml = "<div class=\"advertisement-container\">";
        for (let i = 0; i < productInfo.length; i++) {
            advertisementHtml += `
                <div class="product">
                    <a href="${productInfo[i].link}" class="product-link" target="_blank">
                        <div class="product-image">
                        <img src="${productInfo[i].imageSrc}" alt="${productInfo[i].name}" class="image-large" width="300" height="300">
                        </div>
                        <div class="product-details">
                            <h3>${productInfo[i].name}</h3>
                            <p>Price: ${productInfo[i].price}</p>
                            <p>Quantity: ${productInfo[i].quantity}</p>
                            <a class="product-price-link" href="${productInfo[i].link}" target="_blank">View Details</a>
                        </div>
                    </a>
                </div>`;
        }
        advertisementHtml += "</div>";
    
        return advertisementHtml;
    }
};

// Define inline ads with website links
const inlineAds = {
    "cold": [
        "https://www.cdc.gov", // URL for CDC
        "https://www.mayoclinic.org" // URL for Mayo Clinic
    ],
    "fruits": [
        "https://www.eatright.org", // URL for Eatright.org
        "https://www.choosemyplate.gov" // URL for Choosemyplate.gov
    ]
};

// Function to generate clickable inline ads with the [AD] tag
function generateInlineAdHtml(url) {
    return `<a href="${url}" target="_blank"><sup>[AD]</sup></a>`;
}

// Function to add inline ads after every sentence
function addInlineAds(text, keyword, ads) {
    const sentences = text.split('. ');
    const result = sentences.map((sentence, index) => {
        if (sentence.trim() === "") {
            return sentence;
        }
        if (ads[keyword] && ads[keyword].length > 0) {
            // Choose an ad randomly or in a specific order
            const adIndex = index % ads[keyword].length;
            const adsHtml = generateInlineAdHtml(ads[keyword][adIndex]);
            return `${sentence.trim()}. ${adsHtml}`;
        }
        return `${sentence.trim()}.`;
    });

    return result.join(' ');
}

function formatChatbotResponse(responseText) {
    let formattedText = responseText;

    // Example: Formatting bullet points
    // Assuming bullet points are represented with asterisks (*) in the response
    formattedText = formattedText.replace(/(\* [^\*]+)/g, "<ul><li>$1</li></ul>");
    formattedText = formattedText.replace(/\* /g, "</li><li>");

    // Example: Formatting paragraphs
    // Replace newline characters with paragraph tags
    formattedText = formattedText.replace(/\n\n/g, "</p><p>");

    // Encapsulate in a paragraph tag
    formattedText = "<p>" + formattedText + "</p>";

    return formattedText;
}


// Function to create a chat element
function createChatElement(html, chatClass) {
    const chatDiv = document.createElement("div");
    chatDiv.classList.add("chat");
    chatDiv.classList.add(chatClass);
    chatDiv.innerHTML = html;
    return chatDiv;
}

let activeContexts = [];
// Update the getChatResponse function
const getChatResponse = async (userText) => {
    const API_URL = "https://api.openai.com/v1/chat/completions";
    const customInstructions = localStorage.getItem('customInstructions') || "";
    const chatbotResponseContainer = document.createElement("div");
    chatbotResponseContainer.classList.add("chat", "incoming");
    const chatbotImg = document.createElement('img');
    chatbotImg.src = 'images/chatbot.jpg'; // Replace 'images/chatbot.jpg' with your chatbot image source
    chatbotImg.alt = 'Chatbot';
    chatbotImg.classList.add('user-chatbot-img');
    chatbotResponseContainer.appendChild(chatbotImg);
    const typingIndicator = document.createElement("div");
    typingIndicator.innerHTML = `<span class="typing-indicator"><span></span><span></span><span></span></span>`;
    chatbotResponseContainer.appendChild(typingIndicator);
    chatContainer.appendChild(chatbotResponseContainer);

    try {
        const requestOptions = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${API_KEY}`
            },
            body: JSON.stringify({
                model: "gpt-3.5-turbo",
                messages: [
                    { role: "system", content: "You are a helpful assistant." },
                    // Include custom instructions in the message if they exist
                    ...(customInstructions ? [{ role: "user", content: customInstructions }] : []),
                    { role: "user", content: userText }
                ]
            })
        };

        const response = await (await fetch(API_URL, requestOptions)).json();
        if (response.choices && response.choices.length > 0) {
            let chatbotResponse = response.choices[0].message.content.trim();
            chatbotResponse = formatChatbotResponse(chatbotResponse);
            chatbotResponse = addInlineAds(chatbotResponse, "cold", inlineAds);
            const chatbotResponseContent = document.createElement("p");
            chatbotResponseContainer.appendChild(chatbotResponseContent);
        
            // Stop the typing indicator as the typewriter effect begins
            typingIndicator.innerHTML = "";
        
            // Apply the typewriter effect to the chatbot response
            typewriterEffect(chatbotResponseContent, chatbotResponse, 50);
        
            setTimeout(() => {
                if (showAds) {
                    const advertisementHtml = generateAdvertisement(userText);
                    if (advertisementHtml) {
                        const adElement = document.createElement("div");
                        adElement.innerHTML = advertisementHtml;
                        chatContainer.appendChild(adElement);
                    }
                }
                chatContainer.scrollTo(0, chatContainer.scrollHeight);
            }, chatbotResponse.length * 50);
            // Check if the response contains instructions to set a context
            
            // Use the activeContexts array to maintain multiple active contexts throughout the conversation
            activeContexts.forEach(context => {
                // Implement logic here based on different contexts to ensure continuity in behavior
                if(activeContexts.includes(customInstructions)) {
                    if (customInstructions) {
                        const newContext = customInstructions.toLowerCase();
                        activeContexts.push(newContext); // Add the new context to the activeContexts array
                        customInstructions = ""; // Reset only if there are specific instructions
                    }
                    // Handle responses or behavior for the doctor context
                } else if (context.includes("other_context")) {
                    // Handle responses or behavior for another context
                }
                // Add more conditions for other contexts as needed
            });

        } else {
            typingIndicator.innerHTML = "No response received from the model.";
        }
    } catch (error) {
        console.error("API Error:", error);
        typingIndicator.innerHTML = "Oops! Something went wrong. Please try again.";
    }

    localStorage.setItem("all-chats", chatContainer.innerHTML);
};

function typewriterEffect(element, html, speed) {
    const tempDiv = document.createElement('div');
    tempDiv.innerHTML = html;
    let text = tempDiv.textContent || tempDiv.innerText || "";
    let index = 0;

    function typeCharacter() {
        if (index < text.length) {
            element.textContent += text.charAt(index);
            index++;
            setTimeout(typeCharacter, speed);
        } else {
            // Once typewriting is done, set the full HTML
            element.innerHTML = html;
        }
    }

    typeCharacter();
}

sendButton.addEventListener("click", () => {
    userText = chatInput.value.trim();
    if (!userText) return;

    chatInput.value = "";
    chatInput.style.height = `${initialInputHeight}px`;

    // Display user's query
    displayUserQuery(userText);

    // Get and display ChatGPT response with typewriter effect
    getChatResponse(userText);
});


const handleOutgoingChat = () => {
    userText = chatInput.value.trim();
    if (!userText) return;

    // Clear the input field and reset its height
    chatInput.value = "";
    chatInput.style.height = `${initialInputHeight}px`;

    const html = `<div class="chat-content">
                    <div class="chat-details">
                        <img src="images/user.jpg" alt="user-chatbot-img" class="user-chatbot-img">
                        <p style="display:inline">${userText}</p>
                    </div>
                </div>`;

    const outgoingChatDiv = createChatElement(html, "outgoing");

    const userQueryDivs = chatContainer.querySelectorAll(".user-query");
    userQueryDivs.forEach(userQueryDiv => {
        userQueryDiv.style.display = "none";
    });

    chatContainer.querySelector(".default-text")?.remove();
    chatContainer.appendChild(outgoingChatDiv);
    chatContainer.scrollTo(0, chatContainer.scrollHeight);

    saveUserQuery(userText);
    updateAdsStatus();

    // Call the getChatResponse function to get the response for the user query
    getChatResponse(userText);
};
// Function to handle when a user clicks on a saved query
const handleUserQueryClick = (query) => {
    const savedChatsHTML = localStorage.getItem(`chat-history-${query}`);
    if (savedChatsHTML) {
        chatContainer.innerHTML = savedChatsHTML;
        chatContainer.scrollTo(0, chatContainer.scrollHeight);
        // Prompt the same query
        chatInput.value = query;
        // Send the query to get the chat response
        handleOutgoingChat();
    }
};
// Function to save a user query to the sidebar
// Function to save a user query to the sidebar
const saveUserQuery = (userQuery) => {
    // Get the chat history element
    const chatHistory = document.querySelector('.chat-history');

    // Split the userQuery into words
    const words = userQuery.split(' ');

    // Take the first three words and join them with ' ' separator
    let truncatedQuery = words.slice(0, 3).join(' ');

    // If there are more than three words, add '...' to the truncatedQuery
    if (words.length > 3) {
        truncatedQuery += ' ...';
    }

    // Create a new chat element to represent the user query
    const sidebarChatElement = document.createElement('div');
    sidebarChatElement.classList.add('user-query');
    sidebarChatElement.innerHTML = `<p>${truncatedQuery}</p><br>`; // Removed color and timestamp

    // Append the new chat element to the chat history in the sidebar
    chatHistory.appendChild(sidebarChatElement);

    // Save the updated chat history to local storage with a unique key
    const chatHistoryHTML = chatHistory.innerHTML;
    localStorage.setItem(`chat-history`, chatHistoryHTML);
    localStorage.setItem(`chat-history-${truncatedQuery}`, chatHistoryHTML);
};

const initialInputHeight = chatInput.scrollHeight;

chatInput.addEventListener("input", () => {
    // Adjust the height of the input field dynamically based on its content
    chatInput.style.height = `${initialInputHeight}px`;
    chatInput.style.height = `${chatInput.scrollHeight}px`;
});

chatInput.addEventListener("keydown", (e) => {
    // If the Enter key is pressed without Shift and the window width is larger
    // than 800 pixels, handle the outgoing chat
    if (e.key === "Enter" && !e.shiftKey && window.innerWidth > 800) {
        e.preventDefault();
        handleOutgoingChat();
    }
});


// Initialize the chat by loading data from local storage
loadDataFromLocalstorage();

