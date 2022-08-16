var possessionApi = Vue.resource('/users{/userId}/items{/itemId}');


Vue.component('possession-row', {
    props: ['possession'],
    template: '<tr>' +
        '<td>{{possession.user.surname}} {{possession.user.name}} {{possession.user.patronymic}}</td>' +
        '<td>{{possession.item.name}}</td>' +
        '<td>{{possession.withDate}}</td>' +
        '<td>{{possession.toDate}}</td>' +
        '</tr>'
})

Vue.component('possessions-list', {
    props: ['possessions'],
    data: function () {
        return {
            possession: null
        }
    },
    template:
        '<div>' +
        '<table class="table">' +
        '<thead>' +
        '<tr>' +
        '<th scope="col">User</th>' +
        '<th scope="col">Item</th>' +
        '<th scope="col">With date</th>' +
        '<th scope="col">To date</th>' +
        '</tr>' +
        '</thead>' +
        '<tbody>' +
        '<possession-row v-for="possession in possessions" ' +
        ':possession="possession"/>' +
        '</tbody>' +
        '</table>' +
        '</div>',
    created: function () {
        possessionApi.get().then(result =>
            result.json().then(data =>
                data.forEach(possession =>
                    this.possessions.push(possession))
            )
        )
    },
});

var app = new Vue({
    el: '#archive',
    template: '<possessions-list :possessions="possessions"/>',
    data: {
        possessions: []
    }
});